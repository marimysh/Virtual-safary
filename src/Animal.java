/*public class Animal
{
  void editPhoto(int newPhoto);   //TODO:
  void sound();
  void dist();
}
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

/**
 * Описывает животное, Обязательные характеристики и основные действия.
 * Так же действия, которые должны будут обязательно быть у каждого животного свои
 */
abstract class Animal
{
  /**
   * Создает новый объект при начальных данных
   */
  public Animal(String xname, int xx, int xy, boolean xgender) {
    name = xname;
    kx = xx;
    ky = xy;
    gender = xgender;
    satiety = 99.9;
    mood = 99.9;
  }
  /**
   * Создает полную копию объекта
   */
  public Animal(Animal value)
  {
    this.setName(value.getName());
    this.satiety = value.getSatiety();
    this.setX(value.getX());
    this.setY(value.getY());
    if (value.getGender() == "man")
      this.gender = true;
    else
      this.gender = false;
  }

  /**
   * Создает животное на основе текстовых данных
   * @param value данные в виде массива строк, где улевой элемент - тип
   */
  public Animal(String[] value) {
    this.name = value[1];
    this.kx = Integer.parseInt(value[2]);
    this.ky = Integer.parseInt(value[3]);
    if (value[4] == "man")
      this.gender = true;
    else
      this.gender = false;
  }

  /**
   * @return имя животного
   */
  public String getName() {
    return name;
  }

  /**
   * Меняем имя животного на новое
   */
  public void setName(String newname) {
    name = newname;
  }

  /**
   * @return координата X животного
   */
  public int getX() {
    return kx;
  }

  /**
   * меняется координата X животного на новую
   * @param x
   */
  protected void setX(int x) {
    kx = x;
  }

  /**
   * @return координата Y животного
   */
  public int getY() {
    return ky;
  }

  /**
   * Меняется координата Y животного на новую
   * @param y новое значение Y
   */
  protected void setY(int y) {
    ky = y;
  }

  /**
   * @return Сытость животного на данный момент
   * может изменяться от 0 до 99.9
   */
  public double getSatiety() {
    return satiety;
  }

  /**
   * @return Настроение животного на данный момент
   * может изменятьяс от 0 до 99.9
   */
  public double getMood() {
    return mood;
  }

  /**
   * @return Пол животного.
   * Возвращаемое значение "man" или "woman"
   */
  public String getGender() {
    if (gender)
      return "man";
    else
      return "woman";
  }

  /**
   * @return Сколько дней животному.
   * >= 0
   */
  public int getAge() {
    return age;
  }

  /**
   * @return Большой вариант картинки данного животного
   */
  public ImageIcon getPhoto()
  {
    return photo;
  }

  /**
   * @return Мини изображение животного.
   * По умолчанию для карты
   */
  public Image getPhotoMini() { return photoMini; }

  /**
   * Максимальное количество еды по умолчанию.
   * 100 - т.к. больше этого числа за раз ни одно животное не съест
   */
  public void Eat()
  {
    Eat(100);
  }

  /**
   * Считает сколько съедает животное в этот момент.
   * @param maxFood Максимальное количество еды, которое ему доступно в данный момент.
   */
  public void Eat(int maxFood)
  {
    //Выбирается тип еды мясо/трава
    if (Order())
    {
      Random rand = new Random();
      int meat;
      if (countFood() <= maxFood)
        meat = rand.nextInt(countFood());
      else
        meat = rand.nextInt(maxFood);
      IncreasedSatiety(meat);
    }
    else
    {
      Random rand = new Random();
      int leaves;
      if (countFood() <= maxFood)
        leaves = rand.nextInt(countFood());
      else
        leaves = rand.nextInt(maxFood);
      IncreasedSatiety(leaves);
    }
  }

  /**
   * Преобразует данные животного в строку. Все данные разделены ';'
   * @return Строка с основными данными: Тип, Имя, X, Y, Пол
   */
  protected String mainInfoToString() {
    String res = "";
    res += getType() + ';';
    res += name + ';';
    res += getX() + ";";
    res += getY() + ";";
    res += getGender() + ';';
    return res;
  }

  public abstract boolean Order();        // платоядное / травоядное
  public abstract String voice();         // крик
  public abstract String getType();       // Вид животного
  protected abstract int countFood();     // Максимальное количество еды, которое физически может съесть это животное
  protected abstract int countSleep();    // Время сна
  protected abstract int weight();        // Вес
  public abstract String infoToString();  // Собирает всю информацию о животном в строку

  /**
   * Формула, по которой животное становится более голодным
   * т.е. уменьшается его сытость
   */
  public void LowSatiety() {
    satiety = 0.008 * satiety * satiety + 0.2 * satiety;
  }

  /**
   * Формула, по которой у животного падает настроение
   */
  public void LowMood() {
    mood = 0.008 * mood * mood + 0.2 * mood;
  }

  /**
   * Формула, по которой у животного увеличивается настроение
   */
  protected void IncreasedMood()
  {
    mood = -0.01 * (mood - 100) * (mood - 100) + 100;
    if (mood >= 100)
      mood = 99.9;
  }

  /**
   * Прибавка сытости
   * @param count на сколько увеличить сытость в процентах
   */
  protected void IncreasedSatiety(double count)
  {
    satiety += count;
    if (satiety >= 100)
      satiety = 99.9;
  }

  /**
   * Животное спит
   * @return  сообщение о том, что оно уснуло
   */
  public String Sleep() {
    IncreasedMood();
    //countSleep();
    return "Sleeeeeeeeeeeep";
  }

  /**
   * Выбирает куда хочет пойти животное. Рассматриваются ближайшие клетки в радиусе 1
   * Так же есть вероятность 1/9 что останется стоять на месте
   * @param height  высота карты
   * @param width   ширина карты
   */
  public void walk(int height, int width)
  {
    int newx = getX() + 9, newy = getY() + 5;       //переход в новые координаты.
                                                    //изпользуются для хранения новых координат
    int x = newx, y = newy;                         //координата 0,0 из центра перемещается в верхний левый угол
                                                    //используются для хранения действительных координат
    Random rand = new Random();
    int random = rand.nextInt(9);                   //для выбора случайного хода
    int beginI, beginJ;                             //начало обхода

    if (getX() - 1 <= -(width / 2))                 //чтобы не выйти за границы карты
      beginI = 0;
    else
      beginI = x - 1;
    if (getY() - 1 <= -(height / 2))
      beginJ = 0;
    else
      beginJ = y - 1;
    //удаляем животное из списка, платоядное и травоядное соответственно
    if (Order())
      mapPredator[y][x].remove(this);
    else
      mapHerbivores[y][x].remove(this);
    //рассматриваем все клетки рядом
    for (int i = beginI; i <= x + 1 && i < width; ++i)
      for (int j = beginJ; j <= y + 1 && j < height; ++j)
        if (random == 0 && ((!Order() && mapPredator[j][i].size() == 0) || Order())) {
          setX(i - 9);                                 //время рандома
          setY(j - 5);
          if (Order())
            mapPredator[y][x].add(this);
          else
            mapHerbivores[y][x].add(this);
          return;
        } else if ((!Order() && mapPredator[j][i].size() == 0) || Order()) {
          random--;
          newx = i;
          newy = j;
        }
    setX(newx - 9);
    setY(newy - 5);
    //возвращаем животное в карты с новыми координатами
    if (Order())
      mapPredator[newy][newx].add(this);
    else
      mapHerbivores[newy][newx].add(this);
  }

  /**
   * Ищет где ближайшая еда.
   * Два алгоритма, для плотоядных и травоядных
   * @return true если нашел и покушал
   * false если не нашел или нашел и в пути
   */
  public boolean FindFood() {
    int x = getX() + 9, y = getY() + 5;     //переход в новые координаты
    Vector<Animal>[][]tempMap;              //Карта с животными
    int[][] tempMap2;                       //Или карта с растениями
    if (Order()) {
      tempMap = mapHerbivores.clone();
      if (tempMap[y][x].size() > 0) {
        //Кушаем травоядного
        Message.addElement(this.getName() + " жестоко убил " + mapHerbivores[y][x].elementAt(0).getName());
        Eat(tempMap[y][x].elementAt(0).weight());
        elements.removeElement(tempMap[y][x].elementAt(0));
        mapHerbivores[y][x].remove(0);
        return true;
      }
        for (int radiusNow = 1; radiusNow <= tempMap.length; ++radiusNow)
        //проверяет квадратами с центром = положение животного
        {
          int shift;  //максимальный сдвиг поиска влево/вправо относительно положения животного
          if (radiusNow % 2 == 0)
            shift = radiusNow / 2;
          else
            shift = radiusNow / 2 + 1;
          for (int i = 0; i <= radiusNow && i < tempMap.length; ++i) {
            //4 стороны квадрата
            //нижняя
            if (y + radiusNow < tempMap.length && x - shift + i < tempMap[0].length &&
                x - shift + i >= 0 && tempMap[y + radiusNow][x - shift + i].size() > 0) {
              setX(getX() + ConversionMove(-shift + i));
              setY(getY() + ConversionMove(radiusNow));
              return false;
            }
            //верхняя
            if (y - radiusNow >= 0 && x - shift + i < tempMap[0].length && x - shift + i >= 0 &&
                tempMap[y - radiusNow][x - shift + i].size() > 0) {
              setX(getX() + ConversionMove(-shift + i));
              setY(getY() + ConversionMove(-radiusNow));
              return false;
            }
            //левая
            if (x + radiusNow < tempMap[0].length && y - shift + i < tempMap.length &&
                y - shift + i >= 0 && tempMap[y - shift + i][x + radiusNow].size() > 0) {
              setX(getX() + ConversionMove(radiusNow));
              setY(getY() + ConversionMove(-shift + i));
              return false;
            }
            //правая
            if (x - radiusNow >= 0 && y - shift + i < tempMap.length && y - shift + i >= 0 &&
                tempMap[y - shift + i][x - radiusNow].size() > 0) {
              setX(getX() + ConversionMove(-radiusNow));
              setY(getY() + ConversionMove(-shift + i));
              return false;
            }
          }
        }
      }

    //травоядное
    else {
      //кушаем траву
      tempMap2 = mapPlant.clone();
      if (tempMap2[y][x] > 3) {
        double tempSatiety = getSatiety();
        Eat(tempMap2[y][x]);
        tempSatiety = getSatiety() - tempSatiety;
        tempMap2[y][x] -= tempSatiety;
        return true;
      }
      for (int radiusNow = 1; radiusNow <= tempMap2.length; ++radiusNow)
      //проверяет квадратами с центром = положение животного
      {
        int shift;
        if (radiusNow % 2 == 0)
          shift = radiusNow / 2;
        else
          shift = radiusNow / 2 + 1;
        for (int i = 0; i <= radiusNow && i < tempMap2.length; ++i) {
          //4 стороны квадрата
          //нижняя
          if (y + radiusNow < tempMap2.length && x - shift + i < tempMap2[0].length &&
              x - shift + i >= 0 && tempMap2[y + radiusNow][x - shift + i] > 3) {
            setX(getX() + ConversionMove(-shift + i));
            setY(getY() + ConversionMove(radiusNow));
            return false;
          }
          //верхняя
          if (y - radiusNow >= 0 && x - shift + i < tempMap2[0].length && x - shift + i >= 0 &&
              tempMap2[y - radiusNow][x - shift + i] > 3) {
            setX(getX() + ConversionMove(-shift + i));
            setY(getY() + ConversionMove(-radiusNow));
            return false;
          }
          //левая
          if (x + radiusNow < tempMap2[0].length && y - shift + i < tempMap2.length &&
              y - shift + i >= 0 && tempMap2[y - shift + i][x + radiusNow] > 3) {
            setX(getX() + ConversionMove(radiusNow));
            setY(getY() + ConversionMove(-shift + i));
            return false;
          }
          //правая
          if (x - radiusNow >= 0 && y - shift + i < tempMap2.length && y - shift + i >= 0 &&
              tempMap2[y - shift + i][x - radiusNow] > 3) {
            setX(getX() + ConversionMove(-radiusNow));
            setY(getY() + ConversionMove(-shift + i));
            return false;
          }
        }
      }
    }
    return false;
  }

  /**
   * Животное прожило еще один день
   */
  public void Mature()      //+1 day
  {
    ++age;
  }

  /**
   * Положительное, отрицательное или 0
   * @param x Проверяемое число
   * @return +1 / 0 / -1
   */
  protected int ConversionMove(int x) {
    if (x > 0)
      return 1;
    else if (x < 0)
      return -1;
    else
      return 0;
  }

  /**
   * @return Вид информации о животном в JList
   */
  @Override
  public String toString() {
    int tempSatiety = (int) getSatiety();
    int tempMood = (int) getMood();
    return (name + " " + tempSatiety + " " + getX() + " " + getY() + " " + tempMood);
  }

  //список всех животных
  static DefaultListModel<Animal> elements = new DefaultListModel<>();
  //карта хищников
  static Vector<Animal>[][] mapPredator = new Vector[11][19];
  //карта травоядных
  static Vector<Animal>[][] mapHerbivores = new Vector[11][19];
  //карта растений
  static int mapPlant[][] = new int[11][19];
  //список сообщенек
  static DefaultListModel<String> Message = new DefaultListModel<>();

  //Основные данные животного
  protected String name;
  protected int kx, ky, age;
  protected double satiety, mood;
  protected boolean gender;
  protected ImageIcon photo;
  protected Image photoMini;

  //меняем фото животного
  public void setPhoto(ImageIcon value)
  {
    photo = value;
  }
  //меняем иконку животного
  public void setPhotoMini(Image value) { photoMini = value; }
}


/**
 * Если животное домашнее, то должны быть дополнительные функции
 */
interface Homely
{
  String play();
}

/**
 * Класс Льва
 */
class Lion extends Animal
{
  /**
   * Создание нового льва
   */
  Lion(String xname, int xx, int xy, boolean xgender)
  {
    super(xname, xx, xy, xgender);
    photo = new ImageIcon("lion2.png");
    try {
      photoMini = ImageIO.read(new File("lionMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  Lion(Animal valueCopy)
  {
    super(valueCopy);
  }

  Lion(String[] value)
  {
    super(value);
    photo = new ImageIcon("lion2.png");
    try {
      photoMini = ImageIO.read(new File("lionMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * лев плотояден
   * @return true
   */
  public boolean Order()
  {
    return true;
  }

  /**
   * @return крик Льва
   */
  public String  voice()
  {
    return "ia lion";
  }

  /**
   * @return Лев называется Львом
   */
  public String getType()
  { return "Lion";}

  /**
   * @return лев может съесть максимум за раз столько мяса
   */
  protected int countFood()
  {
    return 30;
  }

  /**
   * @return лев спит столько итераций
   */
  protected int countSleep() {
    return 3;
  }

  /**
   * @return сколько в среднем весит лев
   */
  protected int weight() {
    return 20;
  }

  /**
   * Преобразует данные животного в строку. Все данные разделены ';'
   * @return Строка со всеми данными Льва
   */
  public String infoToString()
  {
    String res = mainInfoToString();
    return res;
  }
}

/**
 * Животное Волк
 */
class Wolf extends Animal
{
  Wolf(String xname, int xx, int xy, boolean xgender)
  {
    super(xname, xx, xy, xgender);
    photo = new ImageIcon("wolf.png");
    try {
      photoMini = ImageIO.read(new File("wolfMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  Wolf(Animal valueCopy)
  {
    super(valueCopy);
  }
  Wolf(String[] value)
  {
    super(value);
    photo = new ImageIcon("wolf.png");
    try {
      photoMini = ImageIO.read(new File("wolfMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Волк плотояден
   * @return true
   */
  public boolean Order()
  {
    return true;
  }

  /**
   * @return крик волка
   */
  public String voice()
  {
    return "ia volk";
  }

  /**
   * Волк называется Волком
   * @return Волк
   */
  public String getType()
  { return "Wolf";}

  /**
   * @returnможет съесть за раз столько мяса волк
   */
  protected int countFood()
  {
    return 27;
  }

  /**
   * @return сколько длится итераций сон волка
   */
  protected int countSleep()
  {
    return 2;
  }

  /**
   * @return вес волка
   */
  protected int weight() {
    return 15;
  }

  /**
   * Преобразует данные животного в строку. Все данные разделены ';'
   * @return Строка со всеми данными Волка
   */
  public String infoToString()
  {
    String res = mainInfoToString();
    return res;
  }
}


/**
 * класс Жирафега
 */
class Giraffe extends Animal
{
  Giraffe(String xname, int xx, int xy, boolean xgender)
  {
    super(xname, xx, xy, xgender);
    photo = new ImageIcon("Giraffe.png");
    try {
      photoMini = ImageIO.read(new File("GiraffeMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  Giraffe(Animal valueCopy)
  {
    super(valueCopy);
  }
  Giraffe(String[] value)
  {
    super(value);
    photo = new ImageIcon("Giraffe.png");
    try {
      photoMini = ImageIO.read(new File("GiraffeMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * жираф травояден
   * @return false
   */
  public boolean Order()
  {
    return false;
  }

  /**
   * @return голос Жирафега
   */
  public String voice()
  {
    return "ia girafeg";
  }

  /**
   * Жирафег есть жирафег
   * @return Жираф
   */
  public String getType()
  { return "Giraffe";}

  /**
   * @return максимальное количество листьев, которое может съесть жираф за раз
   */
  protected int countFood()
  {
    return 10;
  }

  /**
   * @return сон жирафа в итерациях
   */
  protected int countSleep()
  {
    return 1;
  }

  /**
   * @return вес жирафа
   */
  protected int weight() {
    return 80;
  }

  /**
   * Преобразует данные животного в строку. Все данные разделены ';'
   * @return Строка со всеми данными Жирафа
   */
  public String infoToString()
  {
    String res = mainInfoToString();
    return res;
  }
}

/**
 * класс Тигры
 */
class Tiger extends Animal
{
  Tiger(String xname, int xx, int xy, boolean xgender)
  {
    super(xname, xx, xy, xgender);
    photo = new ImageIcon("tigra.png");
    try {
      photoMini = ImageIO.read(new File("tigraMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  Tiger(Animal valueCopy)
  {
    super(valueCopy);
  }
  Tiger(String[] value)
  {
    super(value);
    photo = new ImageIcon("tigra.png");
    try {
      photoMini = ImageIO.read(new File("tigraMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Тигр плотояден
   * @return true
   */
  public boolean Order()
  {
    return true;
  }

  /**
   * @return голос Тигра
   */
  public String voice()
  {
    return "ia tigr";
  }

  /**
   * Тигр называется Тигром
   * @return Тигр
   */
  public String getType()
  { return "Tiger";}

  /**
   * @return максимум сколько может съесть за раз мяса тигр
   */
  protected int countFood()
  {
    return 25;
  }

  /**
   * @return сон тигра в итерациях
   */
  protected int countSleep()
  {
    return 2;
  }

  /**
   * @return вес тигра
   */
  protected int weight() {
    return 20;
  }

  /**
   * Преобразует данные животного в строку. Все данные разделены ';'
   * @return Строка со всеми данными Тигра
   */
  public String infoToString()
  {
    String res = mainInfoToString();
    return res;
  }
}

/**
 * класс Собаки
 */
class Dog extends Animal implements Homely
{
  Dog(String xname, int xx, int xy, boolean xgender)
  {
    super(xname, xx, xy, xgender);
    photo = new ImageIcon("dog.png");
    try {
      photoMini = ImageIO.read(new File("dogMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  Dog(Animal valueCopy)
  {
    super(valueCopy);
  }
  Dog(String[] value)
  {
    super(value);
    photo = new ImageIcon("dog.png");
    try {
      photoMini = ImageIO.read(new File("dogMini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Собака плотоядно
   * @return true
   */
  public boolean Order()
  {
    return true;
  }

  /**
   * @return голос Собаки
   */
  public String voice()
  {
    return "ia sobachko";
  }

  /**
   * Играаааааааать
   * @return Я поиграл так-то
   */
  public String play()
  {
    IncreasedMood();
    return "ia igraiu s palochkoi. ia tupoi";
  }

  /**
   * Собака называется Собакой
   * @return Собака
   */
  public String getType()
  { return "Dog";}

  /**
   * @return максимум, который может съесть за раз собака
   */
  protected int countFood()
  {
    return 20;
  }

  /**
   * @return сон Собаки в итерациях
   */
  protected int countSleep()
  {
    return 2;
  }

  /**
   * @return вес собаки
   */
  protected int weight() {
    return 5;
  }

  /**
   * Преобразует данные животного в строку. Все данные разделены ';'
   * @return Строка со всеми данными Собаки
   */
  public String infoToString()
  {
    String res = mainInfoToString();
    return res;
  }
}

/**
 * Класс Кота
 */
class Cat extends Animal implements Homely
{
  Cat(String xname, int xx, int xy, boolean xgender)
  {
    super(xname, xx, xy, xgender);
    photo = new ImageIcon("garfield2.png");
    try {
      photoMini = ImageIO.read(new File("garfield2Mini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  Cat(Animal valueCopy)
  {
    super(valueCopy);
  }
  Cat(String[] value)
  {
    super(value);
    photo = new ImageIcon("garfield2.png");
    try {
      photoMini = ImageIO.read(new File("garfield2Mini.png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Кот плотояден
   * @return false
   */
  public boolean Order()
  {
    return true;
  }

  /**
   * @return голос кота
   */
  public String voice()
  {
    return "ia coshak";
  }

  /**
   * Играааааааать
   * @return как поиграл Кот
   */
  public String play()
  {
    IncreasedMood();
    return "ia igraiu s klubkom. ia ne tupoi.";
  }

  /**
   * Кот называется котом
   * @return Кот
   */
  public String getType()
  { return "Cat";}

  /**
   * @return сколько максимум можетсъесть за раз кот
   */
  protected int countFood()
  {
    return 20;
  }

  /**
   * @return сон кота в итерациях
   */
  protected int countSleep()
  {
    return 3;
  }

  /**
   * @return вес кота
   */
  protected int weight() {
    return 5;
  }

  /**
   * Преобразует данные животного в строку. Все данные разделены ';'
   * @return Строка со всеми данными Кота
   */
  public String infoToString()
  {
    String res = mainInfoToString();
    return res;
  }
}