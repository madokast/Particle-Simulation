package zrx.gui.testMethod;

import zrx.simulate.basicDataContainer.ImportedMagnet;

import java.awt.*;

public class TestMenu extends Menu {
    private static TestMenu testMenu;
    public static TestMenu getInstance()
    {
        if(testMenu==null)
            testMenu = new TestMenu();

        return testMenu;
    }

    private Menu importedMagnetMenu = new Menu("importedMagnet");
    private MenuItem getMagnetUsingNumber = new MenuItem("getMagnetUsingNumber");
    {
        importedMagnetMenu.add(getMagnetUsingNumber);

        getMagnetUsingNumber.addActionListener(e->{
            TestDialog.getInstance().beginTest("get Magnet Using Number",()->{
                try {
                    String str = TestDialog.getInstance().getLastLine();
                    int n = Integer.parseInt(str);
                    TestDialog.getInstance().appendTextArea(ImportedMagnet.stringPrintMagnetInNumber(n));
                }
                catch (Exception except)
                {
                    except.printStackTrace();
                    TestDialog.getInstance().appendTextArea("error!\n");
                }
            });
        });
    }

    private TestMenu()
    {
        super("Test");
        this.add(importedMagnetMenu);
    }
}