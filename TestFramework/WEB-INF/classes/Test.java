import etu1784.framework.ModelView;

import etu1784.framework.MethodAnnotation;

public class Test {

    private String nom;

    @MethodAnnotation( url = "save.do")
    public ModelView test() {
        ModelView mv = new ModelView();

        mv.setView("/test.jsp");
        mv.addItem("test", this.getNom());

        return mv;
    }
    
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}