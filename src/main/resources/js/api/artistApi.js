export async function getArtists() {
  const res = await fetch("/artists");
  if (!res.ok) throw new Error("Failed to fetch artists");
  return res.json();
}