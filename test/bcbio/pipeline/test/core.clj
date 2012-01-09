(ns bcbio.pipeline.test.core
  (:use [bcbio.pipeline.config]
        [midje.sweet]
        [midje.cascalog]))

(fact?- "Parse input configuration file into experiments to process."
        [[{:config {:outdir "test/data", :outdir-prep "test/data/prep"}
           :sample "Test1", :analysis "variation"}]
         [{:config {:outdir "test/data", :outdir-prep "test/data/prep"}
           :sample "Test2", :analysis "variation"}]]
        (config-to-experiments "test/data/example-config.yaml"))
