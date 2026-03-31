import { toCyrillic } from "../utils/helpers.js";
import { getSongsByArtist } from "../api/songApi.js";
import { createSongList } from "./songList.js";

export function renderArtistList(artists, container) {
  container.innerHTML = "";

  artists.forEach(artist => {
    const li = document.createElement("li");
    li.textContent = toCyrillic(`${artist.name} ${artist.surname}`);

    li.addEventListener("click", async () => {
      // toggle songs
      const existing = li.querySelector(".songs-list");
      if (existing) {
        existing.remove();
        return;
      }

      try {
        const songs = await getSongsByArtist(artist.id);
        const songsUl = createSongList(songs);
        li.appendChild(songsUl);
      } catch (err) {
        console.error("Error loading songs:", err);
      }
    });

    container.appendChild(li);
  });
}