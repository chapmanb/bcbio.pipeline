(defproject bcbio.pipeline "0.0.1-SNAPSHOT"
  :java-source-path "src/java"
  :description "Next-generation sequencing analysis pipelines built on Hadoop and Cascalog."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.8.6"]
                 [clj-yaml "0.3.1"]
                 [fs "1.1.2"]
                 [org.clojars.chapmanb/jnomics "0.1.1-a12"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje "1.3.1" :exclusions [org.clojure/clojure]]
                     [midje-cascalog "0.4.0" :exclusions [org.clojure/clojure midje
                                                          cascalog/cascalog]]]
  :run-aliases {:pipeline bcbio.pipeline.config})
