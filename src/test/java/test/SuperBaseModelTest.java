package test;

import halo.query.model.ModelLoader;
import org.junit.Assert;

public class SuperBaseModelTest {

    static {
        ModelLoader loader = new ModelLoader();
        loader.setModelBasePath("test/bean");
        try {
            loader.makeModelClass();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
