;; Distributed high throughput sequencing alignment leveraging Jnomics tools

(ns bcbio.pipeline.align
  (:import [edu.cshl.schatz.jnomics.tools DistributedNovoalign PairedEndLoader])
  (:require [fs.core :as fs]))

(defn load-paired-end
  "Load paired end data to HDFS as a single file."
  [params]
  (let [out-base (str (fs/file (fs/parent (:fastq params))
                               (fs/name (:fastq params))))
        out-file (str out-base ".pe")]
    (if-not (fs/exists? out-file)
      (PairedEndLoader/main (into-array [(:fastq params) (:fastq-pair params)
                                         out-base])))
    out-file))

(defmulti parallel-align :aligner)

(defmethod parallel-align :novoalign [params]
  "Novoalign distributed alignments"
  (let [pe-file (load-paired-end params)
        out-file (str (fs/file (fs/parent pe-file) (fs/name pe-file)) ".sam")
        args (concat ["-o" "SAM"
                      "-d" (:ref params)
                      "-in" pe-file 
                      "-out" out-file
                      "-novo" "/usr/bin/"])]
    (println args)
    (DistributedNovoalign/main (into-array args))))

