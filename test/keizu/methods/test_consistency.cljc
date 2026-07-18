(ns keizu.methods.test-consistency
  "test_consistency.py — 系図 (keizu) SSoT drift-lock. ADR-2606066000.
  1:1 Clojure port (stdlib unittest → clojure.test)."
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure.string :as str]
            #?(:clj [clojure.java.io :as io])
            #?(:clj [clojure.edn :as cedn])
            [keizu.methods._edn :as edn]))

(def ^:private ont-path
  #?(:clj (io/file "schema" "government-relations-ontology.kotoba.edn")))

(def ^:private lexes
  ["relationEdge" "committeeComposition" "moneyFlowObservation" "networkPost"])

(def ^:private cells
  ["ingest" "committee_graph" "money_graph" "relation_weave" "social_post"])

(defn- manifest []
  #?(:clj (:actor/manifest (cedn/read-string (slurp "manifest.edn")))))

;; ── Tests ──────────────────────────────────────────────────────────────────────
(deftest test-manifest-tier-b
  (is (= "Tier-B" (get (manifest) "tier"))))

(deftest test-manifest-adr-matches-ontology
  (let [m (manifest)]
    (is (str/includes? (get-in m ["adr" "master"]) "2606066000"))
    (is (= "2606066000" (get (edn/load-edn ont-path) ":ontology/adr")))))

(deftest test-manifest-lexicons-exist
  (let [m (manifest)
        declared (set (map #(last (str/split % #"\."))
                           (get m "lexiconNamespaces")))]
    (is (= (set lexes) declared))
    (doseq [name lexes]
      (is (.exists (io/file "lex" (str name ".edn")))
          name))))

(deftest test-manifest-cells-match-tree
  (let [m (manifest)
        names (set (map #(get % "name") (get m "cells")))]
    (is (= (set cells) names))
    (doseq [c cells]
      (is (.exists #?(:clj (io/file "src" "keizu" "cells" c "state_machine.cljc")))))))

(deftest test-lex-ids-match-namespaces
  (let [m (manifest)
        declared (set (get m "lexiconNamespaces"))
        got (set (for [n lexes]
                   (get (edn/load-edn
                          #?(:clj (io/file "lex" (str n ".edn"))))
                        ":id")))]
    (is (= got declared))))

(deftest test-registry-owned-locally
  (is (.exists #?(:clj (io/file "registry" "sources.seed.edn")))))

#?(:clj (defn -main [& _] (run-tests 'keizu.methods.test-consistency)))
