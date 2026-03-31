package server.bootstrap;

import server.model.Artist;
import server.model.Song;

import java.util.List;

public class SampleData {

public static List<Artist> createArtists() {
    return List.of(
        Artist.builder().id(0).name("Zdravko").surname("Čolić").build(),
        Artist.builder().id(1).name("Djordje").surname("Balašević").build(),
        Artist.builder().id(2).name("Bajaga").surname("Instruktori").build(),
        Artist.builder().id(3).name("Riblja").surname("Čorba").build(),
        Artist.builder().id(4).name("Električni").surname("Orgazam").build(),
        Artist.builder().id(5).name("Partibrejkers").surname("").build(),
        Artist.builder().id(6).name("Van").surname("Gogh").build(),
        Artist.builder().id(7).name("Crvena").surname("Jabuka").build(),
        Artist.builder().id(8).name("Hari").surname("Mata Hari").build(),
        Artist.builder().id(9).name("Željko").surname("Joksimović").build()
    );
}

public static List<Song> createSongs() {
    return List.of(

        // Zdravko Colic (0)
        Song.builder().title("Ti si mi u krvi").lyrics("").artistId(0).build(),
        Song.builder().title("April u Beogradu").lyrics("").artistId(0).build(),
        Song.builder().title("Stanica Podlugovi").lyrics("").artistId(0).build(),
        Song.builder().title("Kao moja mati").lyrics("").artistId(0).build(),
        Song.builder().title("Produzi dalje").lyrics("").artistId(0).build(),
        Song.builder().title("Pjevam danju pjevam nocu").lyrics("").artistId(0).build(),
        Song.builder().title("Glavo luda").lyrics("").artistId(0).build(),
        Song.builder().title("Zagrli me").lyrics("").artistId(0).build(),
        Song.builder().title("Ljubav je samo rec").lyrics("").artistId(0).build(),
        Song.builder().title("Esma").lyrics("").artistId(0).build(),

        // Djordje Balasevic (1)
        Song.builder().title("Slovenska").lyrics("").artistId(1).build(),
        Song.builder().title("Ringišpil").lyrics("").artistId(1).build(),
        Song.builder().title("Devojka sa cardas nogama").lyrics("").artistId(1).build(),
        Song.builder().title("Odlazi cirkus").lyrics("").artistId(1).build(),
        Song.builder().title("Ne lomite mi bagrenje").lyrics("").artistId(1).build(),
        Song.builder().title("Prica o Vasi Ladackom").lyrics("").artistId(1).build(),
        Song.builder().title("Boza zvani Pub").lyrics("").artistId(1).build(),
        Song.builder().title("Lepa protina kci").lyrics("").artistId(1).build(),
        Song.builder().title("Samo da rata ne bude").lyrics("").artistId(1).build(),
        Song.builder().title("Neki novi klinci").lyrics("").artistId(1).build(),

        // Bajaga i Instruktori (2)
        Song.builder().title("Moji drugovi").lyrics("").artistId(2).build(),
        Song.builder().title("Plavi safir").lyrics("").artistId(2).build(),
        Song.builder().title("Zažmuri").lyrics("").artistId(2).build(),
        Song.builder().title("442 do Beograda").lyrics("").artistId(2).build(),
        Song.builder().title("Godine prolaze").lyrics("").artistId(2).build(),
        Song.builder().title("Sa druge strane jastuka").lyrics("").artistId(2).build(),
        Song.builder().title("Verujem ne verujem").lyrics("").artistId(2).build(),
        Song.builder().title("Tamara").lyrics("").artistId(2).build(),
        Song.builder().title("Ti se ljubis").lyrics("").artistId(2).build(),
        Song.builder().title("Dobro jutro džezeri").lyrics("").artistId(2).build(),

        // Riblja Corba (3)
        Song.builder().title("Lutka sa naslovne strane").lyrics("").artistId(3).build(),
        Song.builder().title("Kad hodas").lyrics("").artistId(3).build(),
        Song.builder().title("Ostani djubre do kraja").lyrics("").artistId(3).build(),
        Song.builder().title("Volim, volim, volim zene").lyrics("").artistId(3).build(),
        Song.builder().title("Avionu slomicu ti krila").lyrics("").artistId(3).build(),
        Song.builder().title("Nemoj sreco nemoj danas").lyrics("").artistId(3).build(),
        Song.builder().title("Pogledaj dom svoj andjele").lyrics("").artistId(3).build(),
        Song.builder().title("Rock n roll za kucni savet").lyrics("").artistId(3).build(),
        Song.builder().title("Ja sam se lozio na tebe").lyrics("").artistId(3).build(),
        Song.builder().title("Dva dinara druze").lyrics("").artistId(3).build(),

        // Elektricni Orgazam (4)
        Song.builder().title("Igra rokenrol cela Jugoslavija").lyrics("").artistId(4).build(),
        Song.builder().title("Debela devojka").lyrics("").artistId(4).build(),
        Song.builder().title("Krokodili dolaze").lyrics("").artistId(4).build(),
        Song.builder().title("Kapetan Esid").lyrics("").artistId(4).build(),
        Song.builder().title("Bejbe ti nisi tu").lyrics("").artistId(4).build(),
        Song.builder().title("Nebo").lyrics("").artistId(4).build(),
        Song.builder().title("Zlatni papagaj").lyrics("").artistId(4).build(),
        Song.builder().title("Locomotion").lyrics("").artistId(4).build(),
        Song.builder().title("Odelo").lyrics("").artistId(4).build(),
        Song.builder().title("Dokolica").lyrics("").artistId(4).build(),

        // Partibrejkers (5)
        Song.builder().title("Kreni prema meni").lyrics("").artistId(5).build(),
        Song.builder().title("1000 godina").lyrics("").artistId(5).build(),
        Song.builder().title("Hoću da znam").lyrics("").artistId(5).build(),
        Song.builder().title("Put").lyrics("").artistId(5).build(),
        Song.builder().title("Mesečeva kći").lyrics("").artistId(5).build(),
        Song.builder().title("Ona zivi na brdu").lyrics("").artistId(5).build(),
        Song.builder().title("Hipnotisana gomila").lyrics("").artistId(5).build(),
        Song.builder().title("Sirotinjsko carstvo").lyrics("").artistId(5).build(),
        Song.builder().title("Ako si").lyrics("").artistId(5).build(),
        Song.builder().title("Ledeno doba").lyrics("").artistId(5).build(),

        // Van Gogh (6)
        Song.builder().title("Neko te ima nocas").lyrics("").artistId(6).build(),
        Song.builder().title("Klatno").lyrics("").artistId(6).build(),
        Song.builder().title("Ludo luda").lyrics("").artistId(6).build(),
        Song.builder().title("Opasan ples").lyrics("").artistId(6).build(),
        Song.builder().title("Anđele moj brate").lyrics("").artistId(6).build(),
        Song.builder().title("Spavaj").lyrics("").artistId(6).build(),
        Song.builder().title("Nisam lud").lyrics("").artistId(6).build(),
        Song.builder().title("Dodirni me").lyrics("").artistId(6).build(),
        Song.builder().title("Mama").lyrics("").artistId(6).build(),
        Song.builder().title("Kolo").lyrics("").artistId(6).build(),

        // Crvena Jabuka (7)
        Song.builder().title("Dirlija").lyrics("").artistId(7).build(),
        Song.builder().title("Ima nesto od srca do srca").lyrics("").artistId(7).build(),
        Song.builder().title("Tugo nesreco").lyrics("").artistId(7).build(),
        Song.builder().title("Bjezi kiso s prozora").lyrics("").artistId(7).build(),
        Song.builder().title("Zovu nas ulice").lyrics("").artistId(7).build(),
        Song.builder().title("Sanjati").lyrics("").artistId(7).build(),
        Song.builder().title("Uzmi me kad hoces ti").lyrics("").artistId(7).build(),
        Song.builder().title("Nekako s proleca").lyrics("").artistId(7).build(),
        Song.builder().title("Volio bih da si tu").lyrics("").artistId(7).build(),
        Song.builder().title("To mi radi").lyrics("").artistId(7).build(),

        // Hari Mata Hari (8)
        Song.builder().title("Lejla").lyrics("").artistId(8).build(),
        Song.builder().title("Strah me da te volim").lyrics("").artistId(8).build(),
        Song.builder().title("Prsten i zlatni lanac").lyrics("").artistId(8).build(),
        Song.builder().title("Ja nemam snage da te ne volim").lyrics("").artistId(8).build(),
        Song.builder().title("Kao domine").lyrics("").artistId(8).build(),
        Song.builder().title("Navodno").lyrics("").artistId(8).build(),
        Song.builder().title("Ja te volim najvise na svetu").lyrics("").artistId(8).build(),
        Song.builder().title("Emina").lyrics("").artistId(8).build(),
        Song.builder().title("Kad dodje oktobar").lyrics("").artistId(8).build(),
        Song.builder().title("U ljubav vjere nemam").lyrics("").artistId(8).build(),

        // Zeljko Joksimovic (9)
        Song.builder().title("Lane moje").lyrics("").artistId(9).build(),
        Song.builder().title("Nije ljubav stvar").lyrics("").artistId(9).build(),
        Song.builder().title("Drska zeno plava").lyrics("").artistId(9).build(),
        Song.builder().title("Ledja o ledja").lyrics("").artistId(9).build(),
        Song.builder().title("Zaboravljas").lyrics("").artistId(9).build(),
        Song.builder().title("Ljubavi").lyrics("").artistId(9).build(),
        Song.builder().title("Supermen").lyrics("").artistId(9).build(),
        Song.builder().title("Milimetar").lyrics("").artistId(9).build(),
        Song.builder().title("Grlica").lyrics("").artistId(9).build(),
        Song.builder().title("Ponelo me").lyrics("").artistId(9).build()
    );
}
}