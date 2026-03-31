import { getArtists } from "./api/artistApi.js";
import { renderArtistList } from "./components/artistList.js";

document.addEventListener("DOMContentLoaded", async () => {
  const container = document.getElementById("artists-list");

  try {
    const artists = await getArtists();
    renderArtistList(artists, container); // klikabilna lista sa pesmama
  } catch (err) {
    container.innerHTML = "Грешка при учитавању извођача";
    console.error(err);
  }
});