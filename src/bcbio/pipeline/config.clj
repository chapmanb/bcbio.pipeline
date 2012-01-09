;; Run distributed Cascalog pipeline based on information supplied in
;; a YAML input configuration file.

(ns bcbio.pipeline.config
  (:import [bcbio.scheme WholeFile]
           [cascading.tuple Fields])
  (:use [cascalog.api]
        [clojure.java.io])
  (:require [clj-yaml.core :as yaml]
            [clojure.string :as string]
            [cascalog.tap :as tap]
            [cascalog.workflow :as workflow]))

(defmapcatop get-config-exps [config-bytes]
  "Retrieve experiments to process in parallel from YAML config."
  (letfn [(from-byte-array [x]
            (String. (.getBytes x) "UTF-8"))]
    (let [config (-> config-bytes
                     from-byte-array
                     (string/replace #"\u0000" "")
                     yaml/parse-string)
          clean-config (dissoc config :experiments)]
      (map (fn [x] (assoc x :config clean-config)) (:experiments config)))))

(defn hfs-wholefile [path & opts]
  "Read entire file and return as byte-array."
  (let [scheme (-> (:outfields (apply array-map opts) Fields/ALL)
                   workflow/fields
                   WholeFile.)]
    (apply tap/hfs-tap scheme path opts)))

(defn config-to-experiments [config-file]
  "Cascalog query to convert a configuration file into experiments to process."
  (let [source (hfs-wholefile config-file)]
    (<- [?exps]
        (source ?config)
        (get-config-exps ?config :> ?exps)
        (:distinct false))))

(defn -main [config-file]
  (?- (stdout) (config-to-experiments config-file)))
