# Bolt's Journal

## 2025-01-24 - Resource Hinting and Script Placement
**Learning:** For static sites with minimal assets, third-party scripts (like GTM/GA) and form endpoints (Formspree) are often the primary performance bottlenecks due to connection latency. Moving tracking scripts to the <head> with `async` and using `preconnect` hints significantly improves resource discovery and connection setup time without blocking initial render.
**Action:** Always check for external script tags and form actions to apply `preconnect` or `dns-prefetch` hints.
