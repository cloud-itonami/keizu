(babashka.classpath/add-classpath "src:test")
(require '[clojure.test :as t])

(def suites
  '[keizu.cells.test-membrane-flow
    keizu.cells.test-state-machines
    keizu.methods.test-analyze
    keizu.methods.test-autorun
    keizu.methods.test-bridge
    keizu.methods.test-charter-invariants
    keizu.methods.test-consistency
    keizu.methods.test-edn
    keizu.methods.test-export
    keizu.methods.test-ingest
    keizu.methods.test-kotoba
    keizu.methods.test-lexicons
    keizu.methods.test-registry
    keizu.methods.test-social
    keizu.methods.test-sources
    keizu.methods.test-weave
    keizu.murakumo-test
    keizu.repository-contract-test])
(apply require suites)
(let [{:keys [fail error]} (apply t/run-tests suites)]
  (System/exit (if (zero? (+ fail error)) 0 1)))
