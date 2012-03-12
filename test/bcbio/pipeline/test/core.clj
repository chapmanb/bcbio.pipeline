(ns bcbio.pipeline.test.core
  (:use [bcbio.pipeline.config]
        [bcbio.pipeline.align]
        [midje.sweet]
        [midje.cascalog]
        [clojure.java.io]
        [clojure.string :only [trimr]])
  (:require [fs.core :as fs]
            [clojure.java.shell :as shell]))

(defn prep-test-data []
  (let [dl-data [{:fname "100326_FC6107FAAXX.tar.gz"
                  :outdir "100326_FC6107FAAXX"
                  :version 2}
                 {:fname "genomes_automated_test.tar.gz"
                  :outdir "genomes"
                  :version 5}]
        datadir (str (fs/file "test" "data" "dl"))
        dl-url "http://chapmanb.s3.amazonaws.com/"]
    (letfn [(add-full-dir [x]
              (assoc x :outdir (str (fs/file datadir (:outdir x)))))
            (is-old-version? [x]
              (let [version-file (str (fs/file (:outdir x) "VERSION"))]
                (and (fs/exists? version-file)
                     (< (-> version-file slurp trimr Integer/parseInt)
                        (:version x)))))
            (needs-download? [x]
              (or
               (not (fs/exists? (:outdir x)))
               (is-old-version? x)))
            (get-test-data [x]
              (shell/with-sh-dir datadir
                (shell/sh "rm" "-rf" (:outdir x))
                (shell/sh "wget" (str dl-url (:fname x)))
                (shell/sh "tar" "-xzvpf" (:fname x))
                (shell/sh "rm" "-f" (:fname x))))]
      (if-not (fs/exists? datadir)
        (fs/mkdirs datadir))
      (doall
       (->> dl-data
            (map add-full-dir)
            (filter needs-download?)
            (map get-test-data))))))

(let [config-out [[{:config {:outdir "test/data", :outdir-prep "test/data/prep"}
                    :sample "Test1", :analysis "variation"}]
                  [{:config {:outdir "test/data", :outdir-prep "test/data/prep"}
                    :sample "Test2", :analysis "variation"}]]]
  (fact "Parse input configuration file into experiments to process."
    (config-to-experiments "test/data/example-config.yaml")) => (produces config-out))

(against-background [(before :facts (prep-test-data))]
  (fact "Distributed alignment with novoalign"
    (let [dl-dir (fs/file "test" "data" "dl")
          ref-file (str (fs/file dl-dir "genomes" "hg19" "seq" "hg19.fa"))
          fq-base (str (fs/file dl-dir "100326_FC6107FAAXX" "7_100326_FC6107FAAXX_%s.fastq"))]
      (parallel-align {:aligner :novoalign :ref ref-file
                       :fastq (format fq-base "1")
                       :fastq-pair (format fq-base "2")})) => nil))
