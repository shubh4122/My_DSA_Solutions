package DSA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

//Hashsets and Hashmaps usage done here
public class Hashing {
    public static void main(String[] args) {
//        hashSetExample();
        hashMapExample();
    }

    public static void hashMapExample() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("gfg", 1);
        m.put("ide", 39);
        m.put("courses", 42);

        System.out.println(m);

//        Iteration over hashmap
        for (Map.Entry<String, Integer> e : m.entrySet()) {
            System.out.println(e.getKey()+"="+e.getValue());
        }
    }

    public static void hashSetExample() {
        //        HashSets
        //Note in <> Non-primitive types can only be there
        HashSet<String> hset = new HashSet<>();
        hset.add("gfg");
        hset.add("courses");
        hset.add("ide");

        System.out.println(hset);
        System.out.println(hset.contains("ide2"));

//        To iterate over hashset
        Iterator<String> i = hset.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
