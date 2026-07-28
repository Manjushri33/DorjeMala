// DorjeMala service worker
// 1) offline: network-first with a cache fallback
// 2) push notifications from the bot worker

const CACHE = "dorjemala-v2";
const ASSETS = ["./", "./index.html", "./manifest.json", "./icon.svg"];

self.addEventListener("install", e => {
  e.waitUntil(caches.open(CACHE).then(c => c.addAll(ASSETS)).catch(() => {}));
  self.skipWaiting();
});

self.addEventListener("activate", e => {
  e.waitUntil(
    caches.keys().then(keys =>
      Promise.all(keys.filter(k => k !== CACHE).map(k => caches.delete(k)))
    )
  );
  self.clients.claim();
});

self.addEventListener("fetch", e => {
  // Only GET can be cached; caches.put throws on anything else.
  if (e.request.method !== "GET") return;
  e.respondWith(
    fetch(e.request)
      .then(r => {
        // Opaque cross-origin responses are not worth storing.
        if (r && r.status === 200 && r.type === "basic") {
          const copy = r.clone();
          caches.open(CACHE).then(c => c.put(e.request, copy)).catch(() => {});
        }
        return r;
      })
      .catch(() => caches.match(e.request).then(r => r || caches.match("./index.html")))
  );
});

// ── Push notifications ──
self.addEventListener("push", e => {
  let data = { title: "DorjeMala ☸", body: "" };
  try { if (e.data) data = Object.assign(data, e.data.json()); } catch (err) {
    try { data.body = e.data ? e.data.text() : ""; } catch (err2) {}
  }
  e.waitUntil(
    self.registration.showNotification(data.title || "DorjeMala ☸", {
      body: data.body || "",
      icon: "./icon.svg",
      badge: "./icon.svg",
      tag: data.tag || "dorjemala-day",
      renotify: false
    })
  );
});

self.addEventListener("notificationclick", e => {
  e.notification.close();
  e.waitUntil(
    self.clients.matchAll({ type: "window", includeUncontrolled: true }).then(list => {
      for (const c of list) {
        if (c.url.indexOf(self.registration.scope) === 0 && "focus" in c) return c.focus();
      }
      return self.clients.openWindow("./index.html");
    })
  );
});
