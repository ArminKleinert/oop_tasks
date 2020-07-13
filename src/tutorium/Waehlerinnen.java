package tutorium;

public class Waehlerinnen {
    public static class Waehlerin {
        boolean studiAusweisMitFoto;
        boolean ausweis;

        public Waehlerin(boolean studiAusweisMitFoto, boolean ausweis) {
            this.studiAusweisMitFoto = studiAusweisMitFoto;
            this.ausweis = ausweis;
        }
    }

    public Waehlerin create(boolean studiAusweisMitFoto, boolean ausweis) {
        return new Waehlerin(studiAusweisMitFoto, ausweis);
    }
}
