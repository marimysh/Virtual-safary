import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NewAnimalDialogD extends JDialog
{
  /**
   * Диалог для создания нового животного
   * Форма с полями для введения параметров:
   * Имя, координаты X, Y, выбор типа
   */
  public NewAnimalDialogD()
  {
    setTitle("New Animal");
    setSize(450, 300);
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(6, 2, 3, 3));
    panel.add(new Label("Name: "));
    panel.add(nameFi);
    panel.add(new Label("X: "));
    panel.add(xFi);
    panel.add(new Label("Y: "));
    panel.add(yFi);
    panel.add(new Label("Type: "));
    typeCBox.addItem("Lion");
    typeCBox.addItem("Wolf");
    typeCBox.addItem("Giraffe");
    typeCBox.addItem("Tiger");
    typeCBox.addItem("Dog");
    typeCBox.addItem("Cat");
    panel.add(typeCBox);
    panel.add(new Label(("Gender: ")));
    panel.add(manChBox);
    cancelBut.addActionListener(closeAc);
    okBut.addActionListener(saveAction);
    panel.add(okBut);
    panel.add(cancelBut);
    add(panel);
  }

  /**
   * Создание диалога для изменения данных выбранного животного
   *
   * @param value - выбранное животное
   */
  public NewAnimalDialogD(final Animal value)
  {
    setTitle("New Animal");
    setSize(450, 300);
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(5, 2, 3, 3));
    panel.add(new Label("Name: " + value.getName()));
    panel.add(nameFi);
    panel.add(new Label("X: " + value.getX()));
    panel.add(xFi);
    panel.add(new Label("Y: " + value.getY()));
    panel.add(yFi);
    cancelBut.addActionListener(closeAc);
    okBut.addActionListener(new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent actionEvent)
      {
        if (!value.getName().equals(nameFi.getText()) && !"".equals(nameFi.getText()))
          value.setName(nameFi.getText());
        if (!"".equals(xFi.getText()))
        {
          int x = Integer.parseInt(xFi.getText());
          if (value.getX() != x)
            value.setX(x);
        }
        if (!"".equals(yFi.getText()))
        {
          int y = Integer.parseInt(yFi.getText());
          if (value.getY() != y)
            value.setY(y);
        }
        //сохраняем измененное в глобальную переменную
        VirtualSafary.animal = value;
        dispose();
      }
    });
    okBut.setText("Change");
    panel.add(okBut);
    panel.add(cancelBut);
    add(panel);
  }

  JTextField nameFi = new JTextField("", 20);
  JTextField xFi = new JTextField("0", 2);
  JTextField yFi = new JTextField("0", 2);
  JComboBox typeCBox = new JComboBox();
  JButton cancelBut = new JButton("Cancel");
  JButton okBut = new JButton("OK");
  JCheckBox manChBox = new JCheckBox("Man");

  /**
   * Сохраняем новое животное
   */
  class SaveAction implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      String name = nameFi.getText();
      int x = Integer.parseInt(xFi.getText());
      int y = Integer.parseInt(yFi.getText());
      String type = (String) typeCBox.getSelectedItem();
      Animal value;
      boolean gender = manChBox.isSelected();
      switch (type)
      {
        case "Lion":
          value = new Lion(name, x, y, gender);
          Animal.elements.addElement(value);
          Animal.mapPredator[y + 5][x + 9].add(value);
          break;
        case "Wolf":
          value = new Wolf(name, x, y, gender);
          Animal.elements.addElement(value);
          Animal.mapPredator[y + 5][x + 9].add(value);
          break;
        case "Giraffe":
          value = new Giraffe(name, x, y, gender);
          Animal.elements.addElement(value);
          Animal.mapHerbivores[y + 5][x + 9].add(value);
          break;
        case "Tiger":
          value = new Tiger(name, x, y, gender);
          Animal.elements.addElement(value);
          Animal.mapPredator[y + 5][x + 9].add(value);
          break;
        case "Dog":
          value = new Dog(name, x, y, gender);
          Animal.elements.addElement(value);
          Animal.mapPredator[y + 5][x + 9].add(value);
          break;
        case "Cat":
          value = new Cat(name, x, y, gender);
          Animal.elements.addElement(value);
          Animal.mapPredator[y + 5][x + 9].add(value);
          break;
      }
      dispose();
    }
  }

  SaveAction saveAction = new SaveAction();

  /**
   * выход без изменений
   */
  class CloseAction implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      dispose();
    }
  }

  CloseAction closeAc = new CloseAction();
}