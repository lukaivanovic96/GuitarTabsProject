import { getArtists } from "./api/artistApi.js";
import { toCyrillic } from "./utils/helpers.js";

document.addEventListener("DOMContentLoaded", async () => {
  const container = document.getElementById("mini-artists-list");

  try {
    const artists = await getArtists();
    // Prikazi samo 5 ili 10 imena bez toggle pesama
    artists.slice(0, 5).forEach(artist => {
      const li = document.createElement("li");
      li.textContent = toCyrillic(`${artist.name} ${artist.surname}`);
      container.appendChild(li);
    });
  } catch (err) {
    container.innerHTML = "Грешка при учитавању извођача";
    console.error(err);
  }
});