const artists = [
  {
    name : "Pera",
    surname : "Perić"
  },
  {
    name : "Mika",
    surname : "Mikić"
  }
];

let artistList = document.getElementById("artists-list");

function toCyrillic(text) {
  const map = {
    a: "а", b: "б", v: "в", g: "г", d: "д",
    e: "е", z: "з", i: "и", k: "к", l: "л",
    m: "м", n: "н", o: "о", p: "п", r: "р",
    s: "с", t: "т", u: "у", f: "ф",
    đ: "ђ", č : "ч", ć : "ћ",

    A: "А", B: "Б", V: "В", G: "Г", D: "Д",
    E: "Е", Z: "З", I: "И", K: "К", L: "Л",
    M: "М", N: "Н", O: "О", P: "П", R: "Р",
    S: "С", T: "Т", U: "У", F: "Ф"
  };

  const matUpper = {

  };

  return text
    .split("")
    .map(char => map[char] || char)
    .join("");
}

artists.forEach(artist => {
  const li = document.createElement("li");
  li.textContent = `${artist.name} ${artist.surname}`;
  artistList.appendChild(li);
});

document.querySelectorAll('h1').forEach(h1 => {
  h1.textContent = toCyrillic(h1.textContent);
});

document.querySelectorAll("#artists-list li").forEach(li => {
  li.textContent = toCyrillic(li.textContent);
});