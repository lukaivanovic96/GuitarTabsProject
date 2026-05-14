export async function getSongsByArtist(artistId) {
  const res = await fetch(`/artists/${artistId}/songs`);
  if (!res.ok) throw new Error("Failed to fetch songs");
  return res.json();
}