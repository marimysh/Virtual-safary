import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

/**
 * Основной класс с программой
 * Все заисимости, главное окно
 */
public class VirtualSafary
{
  public static void main(String[] agrs) {
    MainFrame frame = new MainFrame();
    frame.setVisible(true);                 //видимость
    frame.setSize(900, 650);                //размеры
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocation(150, 150);            //левый верхний угол
    Animal.elements.addElement(animal);
    Animal.elements.addElement(animal2);
    Animal.elements.addElement(animal3);
    Animal.elements.addElement(animal4);
    Animal.elements.addElement(animal5);
    Animal.elements.addElement(animal6);
    Animal.elements.addElement(animal7);
    Animal.elements.addElement(animal8);
    Random rand = new Random();
    //генерируем карту растений
    for (int i = 0; i < Animal.mapPlant.length; ++i)
      for (int j = 0; j < Animal.mapPlant[i].length; ++j) {
        Animal.mapPlant[i][j] = rand.nextInt(30) + 10;
        if (rand.nextInt(4) <= 2)
          Animal.mapPlant[i][j] = 0;
      }
    //инициализируем карты пустыми
    for (int i = 0; i < 11; ++i)
      for (int j = 0; j < 19; ++j) {
        Animal.mapPredator[i][j] = new Vector<Animal>();
        Animal.mapHerbivores[i][j] = new Vector<Animal>();
      }

    Animal.mapHerbivores[5][9].add(animal);
    Animal.mapPredator[5][9 + 3].add(animal2);
    Animal.mapPredator[5][9 + 1].add(animal3);
    Animal.mapPredator[5][9 - 2].add(animal4);
    Animal.mapPredator[5 - 1][9 - 2].add(animal5);
    Animal.mapHerbivores[5][9 + 2].add(animal6);
    Animal.mapHerbivores[5 + 1][9 + 1].add(animal7);
    Animal.mapHerbivores[5 + 4][9 + 1].add(animal8);
    WorkFile.newFile();
  }
  public static Animal animal = new Giraffe("Nik", 0, 0, true);
  public static Animal animal6 = new Giraffe("Lot", 2, 0, true);
  public static Animal animal7 = new Giraffe("Fod", 1, 1, true);
  public static Animal animal8 = new Giraffe("Killo", 1, 4, true);
  public static Animal animal2 = new Lion("Malik", 3, 0, false);
  public static Animal animal3 = new Tiger("Kozs", 1, 0, true);
  public static Animal animal4 = new Cat("Plor", -2, 0, true);
  public static Animal animal5 = new Dog("Hon", -2, -1, false);
}

/**
 * вся панель на главном окошке
 */
class MainFrame extends JFrame
{
  public MainFrame() {                      //менюшко, панельки
    setTitle("Virtual Safary");

    //меню: Animal(download, Add, Unload), Time
    JMenu AnimalMenu = new JMenu("Animal");
    JMenu TimeMenu = new JMenu("Time");
    JMenuItem downloadItem = AnimalMenu.add(new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        WorkFile.newFile();
        for (int i = 0; i < Animal.elements.size(); ++i)
          WorkFile.write(Animal.elements.get(i).infoToString());
        panel.repaint();
        nameAnimalPanel.repaint();
        infoPanel.repaint();
      }
    });
    downloadItem.setText("download");
    AnimalMenu.add(downloadItem);

    JMenuItem addItem = AnimalMenu.add(new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        JDialog md = new NewAnimalDialogD();
        md.setVisible(true);
      }
    });
    addItem.setText("Add");
    AnimalMenu.add(addItem);

    JMenuItem unloadItem = AnimalMenu.add(new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        WorkFile.read();

      }
    });
    unloadItem.setText("Unload");
    AnimalMenu.add(unloadItem);

    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    menuBar.add(AnimalMenu);
    menuBar.add(TimeMenu);

    //Блок кнопок справа
    //изменить данные животного
    JButton changeButton = new JButton("Change");
    changeButton.addActionListener(changeAc);
    //Итерация со всеми
    JButton okButton = new JButton("OK");
    okButton.addActionListener(okAc);
    //Сон у выбранного
    JButton sleepButton = new JButton("Sleep");
    sleepButton.addActionListener(sleepAc);
    //Поиграть выбранным животным
    JButton playButton = new JButton("Play");
    playButton.addActionListener(playAc);
    //выбранное животное пойдет
    JButton walkButton = new JButton("Walk");
    walkButton.addActionListener(walkAc);
    //выбранное животное пойдет искать еду если голодно
    JButton eatButton = new JButton("Eat");
    eatButton.addActionListener(eatAc);
    //выбранное животное умрет
    JButton dieButton = new JButton("Die");
    dieButton.addActionListener(dieAc);
    panel = new MainPanel();
    setLayout(new BorderLayout());
    add(panel);

    //список с элементами. блок слева
    nameAnimalList = new JList<>(Animal.elements);
    nameAnimalList.setVisibleRowCount(20);
    nameAnimalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(nameAnimalList);
    //если выбрано животное
    nameAnimalList.addListSelectionListener(
        /**
         * если выбрано животное, то оно ищется в списке
         * и по нему обновляется информация на верхней панельке
         */
        new ListSelectionListener()
                                                {
                                                  @Override
                                                  public void valueChanged(ListSelectionEvent Event) {
                                                    String value;
                                                    int indexValue;
                                                    if (nameAnimalList.getSelectedValue() != null)
                                                    {
                                                      value = nameAnimalList.getSelectedValue().getName();
                                                      indexValue = 0;
                                                      while (indexValue < Animal.elements.getSize())
                                                        if (value.equals(Animal.elements.elementAt(indexValue).getName()))
                                                          break;
                                                        else
                                                          indexValue++;
                                                      if (indexValue < Animal.elements.getSize())
                                                        VirtualSafary.animal = Animal.elements.elementAt(indexValue);
                                                      printAnimal();
                                                    }
                                                  }
                                                });

    nameAnimalPanel = new JPanel();
    nameAnimalPanel.add(scrollPane);
    movePanel = new JPanel();
    movePanel.add(changeButton);
    movePanel.add(okButton);
    movePanel.add(playButton);
    movePanel.add(walkButton);
    movePanel.add(eatButton);
    movePanel.add(sleepButton);
    movePanel.add(dieButton);
    movePanel.setLayout(new GridLayout(7, 1));    //количество кнопочек

    //создание текстов с данными животного
    nameLabel.setText(nameS + VirtualSafary.animal.getName());
    nameLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
    kxLabel.setText(kxS + VirtualSafary.animal.getX());
    kxLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
    kyLabel.setText(kyS + VirtualSafary.animal.getY());
    kyLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
    typeLabel.setText(typeS + VirtualSafary.animal.getType());
    typeLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
    genderLabel.setText(genderS + VirtualSafary.animal.getGender());
    genderLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
    ageLabel.setText(ageS + VirtualSafary.animal.getAge());
    ageLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
    satietyLabel.setText(satietyS + VirtualSafary.animal.getSatiety());
    satietyLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
    moodLabel.setText(moodS + VirtualSafary.animal.getMood());
    moodLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
    icon = VirtualSafary.animal.getPhoto();
    comp = new JLabel(icon);
    comp.setPreferredSize(new Dimension(100, 100));
    comp.setBounds(100, 100, 50, 50);
    nameLabel.setLocation(100, 100);
    infoPanel = new JPanel();
    //добавление текстов с данными животного
    infoPanel.add(comp);
    infoPanel.add(nameLabel);
    infoPanel.add(kxLabel);
    infoPanel.add(kyLabel);
    infoPanel.add(typeLabel);
    infoPanel.add(genderLabel);
    infoPanel.add(ageLabel);
    infoPanel.add(satietyLabel);
    infoPanel.add(moodLabel);
    infoPanel.setLayout(new GridLayout(1, 8));
    add(infoPanel, BorderLayout.NORTH);

    //что случилось в сафари, нижняя панелько
    eventText.setLineWrap(true);
    JScrollPane eventScroll = new JScrollPane(eventText);
    eventPanel = new JPanel();
    eventPanel.add(eventScroll);

    panel.setBackground(Color.orange);
    nameAnimalPanel.setBackground(Color.orange);
    movePanel.setBackground(Color.orange);
    add(nameAnimalPanel, BorderLayout.WEST);
    add(movePanel, BorderLayout.EAST);
    add(eventPanel, BorderLayout.SOUTH);
  }

  class okAction implements ActionListener
  {
    /**
     * делает рандомное действие со всеми животинками
     * одна итерация времени
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
      //разделение событий итераций
      eventText.append(" " + '\n');
      for (int i = 0; i < Animal.elements.getSize(); ++i)
      {
        Animal temp = Animal.elements.elementAt(i);
        Animal.elements.elementAt(i).LowSatiety();
        Animal.elements.elementAt(i).LowMood();
        Animal.elements.elementAt(i).Mature();
        Random rand = new Random();
        int var = rand.nextInt(4);
        //если голодно
        Random random = new Random();
        if (temp.getSatiety() < 50 + random.nextInt(25))
        {
          eatAnimal(Animal.elements.elementAt(i));
          continue;
        }
        //если травоядное и рядом с ним хищник, то выбирается вариант убежать
        if (!(temp.Order()) && (Animal.mapPredator[temp.getY() + 5][temp.getX() + 9].size() > 0))
          var = 1;
        switch (var) {
          case 1:
            //погулять
            eventText.append(temp.getName() + " go from " + temp.getX() + " " + temp.getY() + " to ");
              Animal.elements.elementAt(i).walk(11,19);
            eventText.append(temp.getX() + " " + temp.getY() + '\n');
            break;
          case 2:
            //поспать
            eventText.append(Animal.elements.elementAt(i).getName() + " " + Animal.elements.elementAt(i).Sleep() + '\n');
            break;
          case 3:
            //покричать
            eventText.append(Animal.elements.elementAt(i).getName() + " " + Animal.elements.elementAt(i).voice() + '\n');
            break;
        }
      }
      panel.repaint();
      nameAnimalPanel.repaint();
      printAnimal();
    }
  }
  okAction okAc = new okAction();

  class eatAction implements ActionListener
  {
    /**
     * выбранное животное ищет еду
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (nameAnimalList.getSelectedValue() != null) {
        eatAnimal(nameAnimalList.getSelectedValue());
        nameAnimalList.getSelectedValue().Mature();
      }
    }
  }
  eatAction eatAc = new eatAction();

  /**
   * Если голоден, то ищет еду и выдает соответствующее сообщение
   * той или иной ситуации
   * (не хочет, ушел искать туда)
   * @param temp голодное животное
   */
  public void eatAnimal(Animal temp)
  {
    if (temp.getSatiety() > 90)
    {
      eventText.append("Я не голоден... " + temp.getName());
      return;
    }
    int tempX = temp.getX(), tempY = temp.getY();
    double tempSatiety = temp.getSatiety();
    if (temp.FindFood())
    {
      tempSatiety = temp.getSatiety() - tempSatiety;
      eventText.append(temp.getName() + " сожрал " + (int) tempSatiety + " листьев\n");
    }
    else
    {
      eventText.append(temp.getName() + " go from " + tempX + " " + tempY + " to ");
      eventText.append(temp.getX() + " " + temp.getY() + " go к еде!\n");
    }
    printAnimal();
    panel.repaint();
    nameAnimalPanel.repaint();
  }

  class playAction implements ActionListener
  {
    /**
     * Есди домашнее, то может поиграть и выводит сообщение если все успешно
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
      Animal value = nameAnimalList.getSelectedValue();
      if (value instanceof Homely)
      {
        eventText.append(value.getName() + " " + ((Homely) value).play() + '\n');
        value.LowSatiety();
        value.Mature();
      }
      printAnimal();
      nameAnimalPanel.repaint();
    }
  }
  playAction playAc = new playAction();

  class walkAction implements ActionListener
  {
    /**
     * Выбранное животное
     * гуляет, заодно теряет настроение и чувство сытости
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
      Animal temp = VirtualSafary.animal;
      eventText.append(temp.getName() + " go from " + temp.getX() + " " + temp.getY() + " to ");
      temp.walk(11, 19);
      temp.LowMood();
      temp.LowSatiety();
      temp.Mature();
      eventText.append(temp.getX() + " " + temp.getY() + '\n');
      printAnimal();
      panel.repaint();
      nameAnimalPanel.repaint();
    }
  }
  walkAction walkAc = new walkAction();

  class sleepAction implements ActionListener
  {
    /**
     * Выбранное животное спиииит
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (nameAnimalList.getSelectedValue() != null) {
        nameAnimalList.getSelectedValue().Sleep();
        nameAnimalList.getSelectedValue().LowSatiety();
        nameAnimalList.getSelectedValue().Mature();
        printAnimal();
        nameAnimalPanel.repaint();
      }
    }
  }
  sleepAction sleepAc = new sleepAction();

  class dieAction implements ActionListener
  {
    /**
     * а теперь давайте его убьем
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
      Animal.elements.removeElement(nameAnimalList.getSelectedValue());
      printAnimal();
      nameAnimalPanel.repaint();
    }
  }
  dieAction dieAc = new dieAction();

  class changeAction implements ActionListener
  {
    /**
     * Изменим данные животного
     * Вызовем диалог и обновим по нему инфу
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
      JDialog md = new NewAnimalDialogD(nameAnimalList.getSelectedValue());
      Animal.elements.get(nameAnimalList.getSelectedIndex());
      md.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
      md.setVisible(true);
      nameAnimalList.repaint();
      printAnimal();
    }
  }
  changeAction changeAc = new changeAction();

  /**
   * подгоняет данные в нужный вид для перерисовки инфы о животном
   */
  private void printAnimal() {
    String[] valueElementsHome = new String[8];
    if (nameAnimalList.getSelectedValue() != null) {
      Animal temp = nameAnimalList.getSelectedValue();
      int i = 0;
      valueElementsHome[i++] = temp.getName();
      valueElementsHome[i++] = String.valueOf(temp.getX());
      valueElementsHome[i++] = String.valueOf(temp.getY());
      valueElementsHome[i++] = temp.getType();
      valueElementsHome[i++] = temp.getGender();
      valueElementsHome[i++] = String.valueOf(temp.getAge());
      valueElementsHome[i++] = String.valueOf(temp.getSatiety());
      valueElementsHome[i] = String.valueOf(temp.getMood());
      Repaint(valueElementsHome, temp.getPhoto());
    }
  }

  /**
   * Перерисовывает информацию о животном на верхней панели.
   * @param valueLabel Список с данными
   * @param valuePhoto Фото животного
   */
  public void Repaint(String[] valueLabel, ImageIcon valuePhoto)
  {
    int i = 0;
    nameLabel.setText(nameS + valueLabel[i++]);
    kxLabel.setText(kxS + valueLabel[i++]);
    kyLabel.setText(kyS + valueLabel[i++]);
    typeLabel.setText(typeS + valueLabel[i++]);
    genderLabel.setText(genderS + valueLabel[i++]);
    ageLabel.setText(ageS + valueLabel[i++]);
    satietyLabel.setText(satietyS + valueLabel[i++]);
    moodLabel.setText(moodS + valueLabel[i]);
    comp.setIcon(valuePhoto);
  }

  JLabel nameLabel = new JLabel();
  JLabel kxLabel = new JLabel();
  JLabel kyLabel = new JLabel();
  JLabel genderLabel = new JLabel();
  JLabel ageLabel = new JLabel();
  JLabel satietyLabel = new JLabel();
  JLabel typeLabel = new JLabel();
  JLabel moodLabel = new JLabel();
  private String nameS = "Name: ";
  private String kxS = "X: ";
  private String kyS = "Y: ";
  private String genderS = "Gender: ";
  private String ageS = "Age = ";
  private String satietyS = "Satiety: ";
  private String typeS = "";
  private String moodS = "Mood: ";
  ImageIcon icon = new ImageIcon();
  JLabel comp;                            //с картинкой о животном
  JTextArea eventText = new JTextArea(3,60);
  private JList<Animal> nameAnimalList;
  private JPanel nameAnimalPanel;
  private JPanel movePanel;
  private JPanel eventPanel;
  private JPanel infoPanel;
  private MainPanel panel;
}