export function toCyrillic(text) {
  // prvo zamena digrafa (dva znaka u latiničnom)
  const digraphs = {
    nj: "њ", lj: "љ", dž: "џ",
    Nj: "Њ", Lj: "Љ", Dž: "Џ",
    NJ: "Њ", LJ: "Љ", DŽ: "Џ",
  };

  // zamena digrafa prvo, jer su dva karaktera
  Object.keys(digraphs).forEach(dg => {
    const regex = new RegExp(dg, "g");
    text = text.replace(regex, digraphs[dg]);
  });

  // pojedinačni karakteri
  const map = {
    a: "а", b: "б", v: "в", g: "г", d: "д",
    đ: "ђ", e: "е", ž: "ж", z: "з", i: "и",
    j: "ј", k: "к", l: "л", m: "м", n: "н",
    o: "о", p: "п", r: "р", s: "с", t: "т",
    ć: "ћ", u: "у", f: "ф", h: "х", c: "ц",
    č: "ч", š: "ш",

    A: "А", B: "Б", V: "В", G: "Г", D: "Д",
    Đ: "Ђ", E: "Е", Ž: "Ж", Z: "З", I: "И",
    J: "Ј", K: "К", L: "Л", M: "М", N: "Н",
    O: "О", P: "П", R: "Р", S: "С", T: "Т",
    Ć: "Ћ", U: "У", F: "Ф", H: "Х", C: "Ц",
    Č: "Ч", Š: "Ш"
  };

  return text
    .split("")
    .map(char => map[char] || char)
    .join("");
}