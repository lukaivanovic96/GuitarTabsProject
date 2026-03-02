let artistList = "";

document.addEventListener("DOMContentLoaded", () => {
  artistList = document.getElementById("artists-list");

  fetch("/artists")
    .then(response => response.json())
    .then(data => renderArtists(data))
    .catch(err => console.error(err));
});

function toCyrillic(text) {
  const map = {
    a: "а", b: "б", v: "в", g: "г", d: "д",
    e: "е", z: "з", i: "и", k: "к", l: "л",
    m: "м", n: "н", o: "о", p: "п", r: "р",
    s: "с", t: "т", u: "у", f: "ф",
    đ: "ђ", č: "ч", ć: "ћ",

    A: "А", B: "Б", V: "В", G: "Г", D: "Д",
    E: "Е", Z: "З", I: "И", K: "К", L: "Л",
    M: "М", N: "Н", O: "О", P: "П", R: "Р",
    S: "С", T: "Т", U: "У", F: "Ф"
  };

  return text
    .split("")
    .map(char => map[char] || char)
    .join("");
}

function renderArtists(artists) {
  artistList.innerHTML = ""; // očisti listu

  artists.forEach(artist => {
    const li = document.createElement("li");
    li.textContent = `${artist.name} ${artist.surname}`;
    li.textContent = toCyrillic(li.textContent);
    li.addEventListener('click', function () {
      toggleSongs(li, artist.id);
    });
    artistList.appendChild(li);
  });
}

async function toggleSongs(artistElement, artistId) {
  const existingList = artistElement.querySelector(".songs-list");

  // If already open → close it
  if (existingList) {
    existingList.remove();
    return;
  }

  try {
    const response = await fetch(
      // `/artist/${artistId}/songs`
      `/songs`
    );

    if (!response.ok) {
      throw new Error("Failed to fetch songs");
    }

    const songs = await response.json();

    const songsUl = document.createElement("ul");
    songsUl.classList.add("songs-list");

    songs.forEach(song => {
      const songLi = document.createElement("li");
      songLi.textContent = song.title; // change if field name differs
      songsUl.appendChild(songLi);
    });

    artistElement.appendChild(songsUl);

  } catch (error) {
    console.error("Error loading songs:", error);
  }
}


// Naslov
document.querySelectorAll('h1').forEach(h1 => {
  h1.textContent = toCyrillic(h1.textContent);
});
