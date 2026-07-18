# Design pointer — government⟷economy join (payee → org.corp.* bridge)

**Primary spec:** `com-etzhayyim-kanjo/docs/adr/0005-government-economy-join.md`.

## Why this lives here too

keizu owns the side that must change first. Today `:money/payee` is an **opaque
disclosed-name node** (`"jp-vendor-x"`) and the seed has **zero** `org.corp.*`
references, so government money flows cannot reach the corporate supply-chain graph
(kabuto) or the disclosed financials (kanjō). The bridge is a keizu-side addition.

## keizu-side change (step 1–3 of ADR-0005, ungated/offline)

1. **Schema** (`schema/government-relations-ontology.kotoba.edn` +
   `lex/moneyFlowObservation.edn`): add OPTIONAL
   - `:money/payee-corp` — resolved `org.corp.*` id (listed entities ONLY);
   - `:money/payee-corp-confidence` ∈ `:exact | :probable | :unresolved`;
   - `:money/payee-corp-sourcing` — `:synthesized` (the resolution is derived, G5).
   `:money/payee` (the disclosed name) is UNCHANGED.

2. **Cell** `cell:keizu.payee_resolve` — LEI / company-register match, Murakumo-only
   narration. Resolves a payee to `org.corp.*` ONLY when it is a listed legal entity.
   **Private individuals / unlisted payees stay `:unresolved`** — G1 no-doxxing is
   preserved; the bridge is entity↔entity, never entity↔person. ≥2 sources (G3).

3. **Export** resolved money edges as `com.etzhayyim.keizu.moneyFlowObservation` EDN for
   kanjō's `depgraph.cljc` to overlay (payer-organ ─funds→ payee-corp on the supply graph).

## Invariants (unchanged)

Non-adjudicating (G2): the join is an accountability + supply-resilience MAP — "public
money flows toward a fragile single-source chain" is a signal for the funder, never an
allegation of wrongdoing (legal characterization stays with chigiri + counsel). Live
resolution/posting remain Council Lv6+ + operator + member-signature gated (G8).
