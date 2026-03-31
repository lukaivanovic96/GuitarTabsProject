export function createSongList(songs) {
  const ul = document.createElement("ul");
  ul.classList.add("songs-list");

  songs.forEach(song => {
    const li = document.createElement("li");
    li.textContent = song.title;
    ul.appendChild(li);
  });

  return ul;
}