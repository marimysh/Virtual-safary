import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class MainPanel extends JPanel
{
  public MainPanel()                                          //кнопочки
  {
    //берем картинки карты и травы
    try {
      mapSafary = ImageIO.read(new File("Safary2.jpeg"));
      grass = ImageIO.read(new File("grass.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.drawImage(mapSafary, 0, 0, null);
    //рисуем животных
    for (int i = 0; i < Animal.elements.getSize(); ++i)
    {
      Animal temp = Animal.elements.elementAt(i);
      g.drawImage(temp.getPhotoMini(), temp.getX() * 40 + 40 * 9 + 3, temp.getY() * 40 + 40 * 5 + 3, null);
    }
    //поверх них рисуем траву
    for (int i = 0; i < Animal.mapPlant.length; ++i)
      for (int j = 0; j < Animal.mapPlant[i].length; ++j)
        if (Animal.mapPlant[i][j] > 3)
          g.drawImage(grass, j * 40 + 10, i * 40 + 10, null);
  }

  private Image mapSafary;
  private Image grass;
}