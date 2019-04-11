package eloi.ancellin.finalproject.modele;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Item extends BaseItem implements Serializable {



        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private String name;
        private int qty;
        private int prio;
        private float price;



        /**
         * Default constructor
         */
        public Item() {
            super();
        }

        /**
         * Constructeur initialize attributs with arguemtns
         * @param name
         * @param qty
         * @param prio
         * @param price
         */
        public Item(String name, int qty, int prio, float price) {
            super();
            this.name = name;
            this.qty = qty;
            this.prio = prio;
            this.price = price;
        }
        public Item(String name, int qty, int prio, float price, String desc) {
            super(desc);
            this.name = name;
            this.qty = qty;
            this.prio = prio;
            this.price = price;
        }


        /**
         * Constructor initializing with another object.
         * @param item
         */
        public Item(Item item) {
            this.name  = item.getName();
            this.qty   = item.getQty();
            this.prio  = item.getPrio();
            this.price = item.getPrice();
        }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return prio == item.prio &&
                Float.compare(item.price, price) == 0 &&
                Objects.equals(name, item.name);
    }


    // getters and setters.
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getQty() {
            return qty;
        }
        public void setQty(int qty) {
            this.qty = qty;
        }
        public int getPrio() {
            return prio;
        }
        public void setPrio(int prio) {
            this.prio = prio;
        }
        public float getPrice() {
            return price;
        }
        public void setPrice(float price) {
            this.price = price;
        }


        public String serialize(){
            String str="";
            str+= name.toString();
            str+= ',';
            str+= Float.toString(price);
            str+=",";
            str+= Integer.toString(qty);
            str+=",";
            str+=Integer.toString(prio);
            return str;
        }
        public static Item deserialize(String str){
            String[] strArr = str.split(",");
            String name= strArr[0];
            float price = Float.parseFloat(strArr[1]);
            int qty = Integer.parseInt(strArr[2]);
            int prio = Integer.parseInt(strArr[3]);
            return new Item(name, qty, prio, price);
        }

        public static  ArrayList<String> serializeArrayIt(ArrayList<Item> arrIt){
             ArrayList<String> arrStr= new ArrayList<String>();
             for(int i=0; i<arrIt.size();i++){
                arrStr.add(arrIt.get(i).serialize());
            }
            return arrStr;
        }
        public static ArrayList<Item> deserializeArrayIt(ArrayList<String> arrStr){
            ArrayList<Item> arrIt= new ArrayList<Item>();
            for(int i=0; i<arrStr.size();i++){
                    arrIt.add(Item.deserialize(arrStr.get(i)));
            }
            return arrIt;
        }

    }


