# com-etzhayyim-keizu

`keizu`（系図）は政府の公開役職・委員会・資金・発言を結ぶ relation-graph actor です。
旧 `etzhayyim/root/20-actors/keizu` の実装と契約はこの flat west project が所有します。

## Repository contract

- metadata、identity、dependencies、schema、registry、seed の正規形式は EDN。
- production source は `src/keizu/`、tests は `test/keizu/`。
- JSON/JSON-LD は外部 wire 互換に限り `wire/` へ置く。
- Go/TinyGo、Python、shell runner は deprecated。
- G1 public-role-only、G2 non-adjudicating、G3 source provenance、G4 edge-primary、
  G5 mirror-not-target、G7 no-server-key、G8 outward-gated を維持する。

## Test

    bb run_tests.clj
    clojure -M:test

Deployment entry is `src/keizu/mesh.clj` and is declared by `kotoba.app.edn`.
