import etu1784.framework.ModelView;

import etu1784.framework.MethodAnnotation;

public class Test {

    @MethodAnnotation( url = "test.do")
    public ModelView test() {
        ModelView mv = new ModelView();

        mv.setView("/test.jsp");
        mv.addItem("test", "Valeur depuis model-view");

        return mv;
    } 
}