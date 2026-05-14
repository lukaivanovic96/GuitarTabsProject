export async function getArtists() {
  const response = await fetch("/artists");
  if (!response.ok) throw new Error("Failed to fetch artists");
  return response.json();
}

export async function createArtist(artist) {
  const response = await fetch("/artists", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(artist)
  });

  if (!response.ok) {
    throw new Error("Failed to create artist");
  }

  return response.json();
}