// import { getArtists } from "../api/artistApi.js";
// import { renderArtistList } from "../components/artistList.js";


// export async function renderHomePage() {
//   const container = document.getElementById("artists-list");
//   container.innerHTML = "Loading...";

//   try {
//     const artists = await getArtists();
//     renderArtistList(artists, container);
//   } catch (err) {
//     container.innerHTML = "Greška pri učitavanju izvođača";
//     console.error(err);
//   }
// }