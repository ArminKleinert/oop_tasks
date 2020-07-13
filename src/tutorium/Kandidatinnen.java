package tutorium;

import java.util.HashSet;
import java.util.Set;

public class Kandidatinnen {
    public static class Kandidatin {
        Set<Waehlerinnen.Waehlerin> waehlerinnen;
        boolean waehlbar;

        public Kandidatin(boolean waehlbar) {
            waehlerinnen = new HashSet<>();
            this.waehlbar = waehlbar;
        }
    }

    int invalideStimmen;
    Kandidatin[] kandidatinnen;

    public Kandidatinnen() {
        kandidatinnen = new Kandidatin[8];
        for (int i = 0; i < kandidatinnen.length; i++) {
            kandidatinnen[i] = new Kandidatin(true);
        }
    }

    public void addStimme(Kandidatin k, Waehlerinnen.Waehlerin w) {
        if (w == null || k == null) {
            throw new NullPointerException();
        }

        if (k.waehlerinnen.contains(w)) {
            throw new IllegalArgumentException("Schon von dieser Wählerin gewählt.");
        }

        if (w.studiAusweisMitFoto && w.ausweis) {
            k.waehlerinnen.add(w);
        } else {
            invalideStimmen++;
        }
    }

    public Kandidatin winner() {
        Kandidatin higher, lower;

        higher = kandidatinnen[0];
        lower = kandidatinnen[1];
        if (higher.waehlerinnen.size() > lower.waehlerinnen.size()) {
            higher = kandidatinnen[1];
            lower = kandidatinnen[0];
        }

        for (int i = 2; i < kandidatinnen.length; i++) {
            if (kandidatinnen[i].waehlerinnen.size() > higher.waehlerinnen.size()) {
                lower = higher;
                higher = kandidatinnen[i];
            } else if (kandidatinnen[i].waehlerinnen.size() > lower.waehlerinnen.size()) {
                lower = kandidatinnen[i];
            }
        }

        int diff = higher.waehlerinnen.size() - lower.waehlerinnen.size();

        return higher;
    }
}
