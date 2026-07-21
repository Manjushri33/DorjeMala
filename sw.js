const CACHE = "mala-v3";
const ASSETS = ["./manifest.json", "./icon.svg"];

self.addEventListener("install", e => {
  self.skipWaiting();
  e.waitUntil(caches.open(CACHE).then(c => c.addAll(ASSETS)).catch(() => {}));
});

self.addEventListener("activate", e => {
  e.waitUntil(
    caches.keys()
      .then(keys => Promise.all(keys.filter(k => k !== CACHE).map(k => caches.delete(k))))
      .then(() => self.clients.claim())
  );
});

self.addEventListener("fetch", e => {
  const req = e.request;
  const isHTML = req.mode === "navigate" || req.destination === "document";
  if (isHTML) {
    e.respondWith(fetch(req).catch(() => caches.match("./index.html")));
    return;
  }
  e.respondWith(
    fetch(req)
      .then(r => { const copy = r.clone(); caches.open(CACHE).then(c => c.put(req, copy)).catch(()=>{}); return r; })
      .catch(() => caches.match(req))
  );
});
