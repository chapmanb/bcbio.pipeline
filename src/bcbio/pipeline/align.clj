;; Distributed high throughput sequencing alignment leveraging Jnomics tools

(ns bcbio.pipeline.align
  (:import [edu.cshl.schatz.jnomics.tools DistributedNovoalign]))

(defmulti parallel-align :aligner)

(defmethod parallel-align :novoalign [params]
  "Novoalign distributed alignments"
  (let [args (concat ["-o" "SAM"
                      "-d" (:ref params)
                      "-in" (:fastq params)])]
    (println args)
    (DistributedNovoalign/main (into-array args))))
