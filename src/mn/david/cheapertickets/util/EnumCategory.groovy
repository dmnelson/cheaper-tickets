package mn.david.cheapertickets.util

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 20/08/11
 * Time: 19:44
 */
@Category(Enum)
class EnumCategory {

    public Object asType2(Class type){
        if(type == Integer || type == int){
            return  ordinal();
        }
        return super.asType(type);
    }
}
