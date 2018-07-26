import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static boolean czy_w_jednym_zbiorze(LinkedList<Integer>[] zbiory_rozlaczne,int poczatek, int koniec){
        int nr_zbioru_poczatku=0;
        int nr_zbioru_konca=0;

        for(int i=1; i<zbiory_rozlaczne.length; i++){
            for(int j=0; j< zbiory_rozlaczne[i].size();j++){
                if(poczatek == zbiory_rozlaczne[i].get(j)) {
                    nr_zbioru_poczatku = i;
                    break;
                }
            }
        }

        for(int i=1; i<zbiory_rozlaczne.length; i++){
            for(int j=0; j< zbiory_rozlaczne[i].size();j++){
                if(koniec == zbiory_rozlaczne[i].get(j)) {
                    nr_zbioru_konca = i;
                    break;
                }
            }
        }

        if(nr_zbioru_poczatku == nr_zbioru_konca) return true;
        else{
            int rozmiar = zbiory_rozlaczne[nr_zbioru_konca].size();
            for(int i=0; i<rozmiar;i++){
                zbiory_rozlaczne[nr_zbioru_poczatku].add(zbiory_rozlaczne[nr_zbioru_konca].get(i));
            }
            zbiory_rozlaczne[nr_zbioru_konca].clear();
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
	    File plik_in = new File("In0303.txt");
        Scanner scanner = new Scanner(plik_in);
        int n,m;                                        //n - liczba wierzchlkow
                                                        //m - liczba krawedzi grafu
        n = scanner.nextInt();
        m = scanner.nextInt();

        LinkedList<Krawedz> lista = new LinkedList<Krawedz>();
        scanner.nextLine();
        //WCZYTANIE Z PLIKU KRAWEDZI
        for(int i = 1; i<=n;i++){
            int j=0;
            String linia = scanner.nextLine();
            int koniec;
            int waga;
            Scanner linia_scanner = new Scanner(linia);

            while(linia_scanner.hasNext()){
                koniec = linia_scanner.nextInt();
                waga = linia_scanner.nextInt();
                if(koniec < i) continue;
                lista.add(new Krawedz(waga,i,koniec));
                j++;
            }
        }

        //SORTOWANIE LISTY

        Collections.sort(lista, new Comparator<Krawedz>() {
            @Override
            public int compare(Krawedz o1, Krawedz o2) {
                return (o1.waga > o2.waga) ? 1: -1;
            }
        });
        LinkedList<Krawedz> lista_out = new LinkedList<>();
        LinkedList<Integer>[] zbiory_rozlaczne = new LinkedList[n+1];
        for(int i=1;i<=n;i++){
            zbiory_rozlaczne[i] = new LinkedList<>();
            zbiory_rozlaczne[i].add(i);
        }
        int i=0;
        while(lista_out.size() < n-1){
            int poczatek = lista.get(i).poczatek;
            int koniec = lista.get(i).koniec;
            if(czy_w_jednym_zbiorze(zbiory_rozlaczne,poczatek,koniec) == false){
                lista_out.add(lista.get(i));
            }
            i++;
        }

        int suma_wag=0;
        for(i=0; i<lista_out.size();i++){
            suma_wag+= lista_out.get(i).waga;
        }

        FileWriter plik_out = new FileWriter("Out0303.txt");

        plik_out.write(suma_wag+"\n");
        for(i=0;i<lista_out.size();i++){
            plik_out.write(lista_out.get(i).poczatek +" "+ lista_out.get(i).koniec+ " ["+ lista_out.get(i).waga +"]\n");
        }
        plik_out.close();
    }
}
