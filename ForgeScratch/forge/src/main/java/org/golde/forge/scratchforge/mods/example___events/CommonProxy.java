package org.golde.forge.scratchforge.mods.example___events;

import org.golde.forge.scratchforge.base.common.block.*;
import org.golde.forge.scratchforge.base.common.item.*;
import org.golde.forge.scratchforge.base.common.world.*;
import org.golde.forge.scratchforge.base.helpers.*;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.settings.*;
import net.minecraft.command.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.dispenser.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.rcon.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.profiler.*;
import net.minecraft.server.*;
import net.minecraft.server.dedicated.*;
import net.minecraft.server.gui.*;
import net.minecraft.server.integrated.*;
import net.minecraft.server.management.*;
import net.minecraft.src.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.demo.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.storage.*;

import net.minecraftforge.classloading.*;
import net.minecraftforge.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.sound.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.minecart.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.terraingen.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.oredict.*;
import net.minecraftforge.transformers.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.common.gameevent.TickEvent.*;
import cpw.mods.fml.common.gameevent.InputEvent.*;
import cpw.mods.fml.common.gameevent.PlayerEvent.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.relauncher.*;

import org.apache.logging.log4j.*;

import java.text.*;
import java.util.*;
import java.lang.*;

import io.netty.buffer.*;
import io.netty.channel.*;

public class CommonProxy {

	public static Scheduler scheduler = new Scheduler();
	
	/* Block Variables */
	

	/* BlockFlower Variables */
	

	/* BlockPlant Variables */
	

	/* Item Variables */
	public static SpawnEgg spawnEgg;
	

	/* Entity Variables */
	/*Variables - Entity*/

	public void preInit(FMLPreInitializationEvent event){
		/* Block Constructor Calls */
		

		/* BlockFlower Constructor Calls */
		

		/* BlockPlant Constructor Calls */
		

		/* Item Constructor Calls */
		spawnEgg = new SpawnEgg(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB);
		

		/* Entity Constructor Calls */
		
	}

	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
		
		/* Recipe Registry */
		
	}
	
	public void serverLoad(FMLServerStartingEvent event) {
		/* Command Registry */
		
	}
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		scheduler.update();
	}

	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		for (int i = 0; i < 20; i++) {
			int x = chunkX + random.nextInt(16) + 8;
			int y = random.nextInt(128);
			int z = chunkZ + random.nextInt(16) + 8;

			/*Overworld world generation for flowers*/
			

			/*Overworld world generation for plants*/
			

		}
	}

	//TODO: Implement nether plant generation?
	public void generateNether(World world, Random random, int chunkX, int chunkZ) {
		for (int i = 0; i < 20; i++) {
			int x = chunkX + random.nextInt(16) + 8;
			int y = random.nextInt(128);
			int z = chunkZ + random.nextInt(16) + 8;

			/*Nether generation for flowers*/
			/*WorldGen - Nether - Flowers*/

			/*Nether generation for plants*/
			/*WorldGen - Nether - Plant*/

		}
	}
	
	public void createEntity(Class entityClass, String rawEntityName, String entityName, int solidColor, int spotColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, rawEntityName, id);
		EntityRegistry.registerModEntity(entityClass, rawEntityName, id, ForgeMod.INSTANCE, 64, 1, true);
		if(solidColor != -1 && spotColor != -1) {
			createEgg(id, solidColor, spotColor);
		}
		//TODO: Add language
	}
	
	private void createEgg(int id, int solidColor, int spotColor) {
		spawnEgg.entityEggs.put(Integer.valueOf(id), new EntityList.EntityEggInfo(id, solidColor, spotColor));
	}

	
/*type:global*/





/*type:event*/
@SubscribeEvent
public void attackEntityEvent(AttackEntityEvent event) {
    if(event.entityPlayer != null && !event.entityPlayer.worldObj.isRemote) {
         final EntityPlayer player = event.entityPlayer;
         final World world = player.worldObj;
         final VariableHolder variableHolder = new VariableHolder();
         if(event.target instanceof EntityLiving) {variableHolder.entity = (EntityLiving)event.target;}
             if(variableHolder.entity != null){
        variableHolder.entity.setCustomNameTag(((variableHolder.entity != null ? variableHolder.entity.getHealth():0) + " " + ModHelpers.getChatColorFromHex("#ff5555") + "\u2764"));
        variableHolder.entity.setAlwaysRenderNameTag(true);
    }

     }
}

/*type:global*/
/**
 *
 * @author bmoon
 */
final static class Var implements Comparable {

    public enum Type {

         STRING, INT, DOUBLE, LIST, NULL, UNKNOWN
    };

    private Type _type;
    private Object _object;
    private static final NumberFormat _formatter = new DecimalFormat("#.#####");

    /**
     * Construct a Var with an UNKNOWN type
     *
     */
    public Var() {
         _type = Type.UNKNOWN;
    } // end var

    /**
     * Construct a Var and assign its contained object to that specified.
     *
     * @param object The value to set this object to
     */
    public Var(Object object) {
         setObject(object);
    } // end var

    /**
     * Construct a Var from a given Var
     *
     * @param var var to construct this one from
     */
    public Var(Var var) {
         setObject(var.getObject());
    } // end var

    /**
     * Static constructor to make a var from some value.
     *
     * @param val some value to construct a var around
     * @return the Var object
     */
    public static Var valueOf(Object val) {
         return new Var(val);
    } // end valueOf

    /**
     * Get the type of the underlying object
     *
     * @return Will return the object's type as defined by Type
     */
    public Type getType() {
         return _type;
    } // end getType

    /**
     * Get the contained object
     *
     * @return the object
     */
    public Object getObject() {
         return _object;
    } // end getObject

    /**
     * Clone Object
     *
     * @return a new object equal to this one
     */
    public Object cloneObject() {
         Var tempVar = new Var(this);
         return tempVar.getObject();
    } // end cloneObject

    /**
     * Get object as an int. Does not make sense for a "LIST" type object
     *
     * @return an integer whose value equals this object
     */
    public int getObjectAsInt() {
         switch (getType()) {
                case STRING:
                     return Integer.parseInt((String) getObject());
                case INT:
                     return (Integer) getObject();
                case DOUBLE:
                     return new Double((Double) getObject()).intValue();
                case LIST:
                     // has no meaning
                     break;
                default:
                     // has no meaning
                     break;
         }
         return 0;
    } // end getObjectAsInt

    /**
     * Get object as a double. Does not make sense for a "LIST" type object.
     *
     * @return a double whose value equals this object
     */
    public double getObjectAsDouble() {
         switch (getType()) {
                case STRING:
                     return Double.parseDouble((String) getObject());
                case INT:
                     return new Integer((Integer) getObject()).doubleValue();
                case DOUBLE:
                     return (Double) getObject();
                case LIST:
                     // has no meaning
                     break;
                default:
                     // has no meaning
                     break;
         }
         return 0.0;
    } // end get object as double

    /**
     * Get object as a string.
     *
     * @return The string value of the object. Note that for lists, this is a
     * comma separated list of the form {x,y,z,...}
     */
    public String getObjectAsString() {
         return this.toString();
    } // end gotObjectAsString

    /**
     * Get the object as a list.
     *
     * @return a List whose elements are of type Var
     */
    public List<Var> getObjectAsList() {
         return (List<Var>) getObject();
    } // end getObjectAsList

    /**
     * If this object is a linked list, then calling this method will return the
     * Var at the index indicated
     *
     * @param index the index of the Var to read (0 based)
     * @return the Var at that index
     */
    public Var get(int index) {
         return ((List<Var>) getObject()).get(index);
    } // end get

    /**
     * If this object is a linked list, then calling this method will return the
     * size of the linked list.
     *
     * @return size of list
     */
    public int size() {
         return ((List<Var>) getObject()).size();
    } // end size

    /**
     * Set the value of of a list at the index specified. Note that this is only
     * value if this object is a list and also note that index must be in
     * bounds.
     *
     * @param index the index into which the Var will be inserted
     * @param var the var to insert
     */
    public void set(int index, Var var) {
         ((List<Var>) getObject()).add(index, var);
    } // end set

    /**
     * Add all values from one List to another. Both lists are Var objects that
     * contain linked lists.
     *
     * @param var The list to add
     */
    public void addAll(Var var) {
         ((List<Var>) getObject()).addAll(var.getObjectAsList());
    } // end addAll

    /**
     * Set the value of the underlying object. Note that the type of Var will be
     * determined when setObject is called.
     *
     * @param val the value to set this Var to
     */
    public void setObject(Object val) {
         this._object = val;
         inferType();
         // make sure each element of List is Var if type is list
         if (_type.equals(Var.Type.LIST)) {
                List<Var> myList = new ArrayList();
                for (Object obj : this.getObjectAsList()) {
                     myList.add(new Var(obj));
                }
                this._object = myList;
         }
    } // end setObject

    /**
     * Add a new member to a Var that contains a list. If the Var current is not
     * of type "LIST", then this Var will be converted to a list, its current
     * value will then be stored as the first member and this new member added
     * to it.
     *
     * @param member The new member to add to the list
     */
    public void add(Var member) {
         if (_type.equals(Var.Type.LIST)) {
                // already a list
                ((List<Var>) _object).add(member);
         } else {
                // not current a list, change it
                List<Var> temp = new ArrayList();
                temp.add(new Var(member));
                setObject(temp);
         }
    } // end add

    /**
     * Increment Object by some value.
     *
     * @param inc The value to increment by
     */
    public void incrementObject(double inc) {
         switch (getType()) {
                case STRING:
                     // has no meaning
                     break;
                case INT:
                     this.setObject((int) (this.getObjectAsInt() + inc));
                     break;
                case DOUBLE:
                     this.setObject((double) (this.getObjectAsDouble() + inc));
                     break;
                case LIST:
                     for (Var myVar : this.getObjectAsList()) {
                            myVar.incrementObject(inc);
                     }
                     break;
                default:
                     // has no meaning
                     break;
         } // end switch
    } // end incrementObject

    /**
     * Increment Object by some value
     *
     * @param inc The value to increment by
     */
    public void incrementObject(int inc) {
         switch (getType()) {
                case STRING:
                     // has no meaning
                     break;
                case INT:
                     this.setObject((int) (this.getObjectAsInt() + inc));
                     break;
                case DOUBLE:
                     this.setObject((double) (this.getObjectAsDouble() + inc));
                     break;
                case LIST:
                     for (Var myVar : this.getObjectAsList()) {
                            myVar.incrementObject(inc);
                     }
                     break;
                default:
                     // has no meaning
                     break;
         }// end switch
    } // end incrementObject

    @Override
    public int hashCode() {
         int hash = 5;
         hash = 43 * hash + Objects.hashCode(this._type);
         hash = 43 * hash + Objects.hashCode(this._object);
         return hash;
    }

    /**
     * Test to see if this object equals another one. This is done by converting
     * both objects to strings and then doing a string compare.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
         final Var other = Var.valueOf(obj);
         if (getType() == Var.Type.NULL || other.getType() == Var.Type.NULL) {
                return getType().equals(other.getType());
         }
         return this.toString().equals(other.toString());
    } // end equals

    /**
     * Check to see if this Var is less than some other var.
     *
     * @param var the var to compare to
     * @return true if it is less than
     */
    public boolean lessThan(Var var) {
         switch (getType()) {
                case STRING:
                     return this.getObjectAsString().compareTo(var.getObjectAsString()) < 0;
                case INT:
                     return this.getObjectAsInt() < var.getObjectAsDouble();
                case DOUBLE:
                     return this.getObjectAsDouble() < var.getObjectAsDouble();
                case LIST:
                     if (size() != var.size()) {
                            return false;
                     }
                     if (!var.getType().equals(Var.Type.LIST)) {
                            return false;
                     }
                     int index = 0;
                     for (Var myVar : this.getObjectAsList()) {
                            if (!myVar.lessThan(var.get(index))) {
                                return false;
                            }
                     }
                     return true;
                default:
                     return false;
         }// end switch
    } // end less than

    /**
     * Check to see if this var is less than or equal to some other var
     *
     * @param var the var to compare to
     * @return true if this is less than or equal to var
     */
    public boolean lessThanOrEqual(Var var) {
         switch (getType()) {
                case STRING:
                     return this.getObjectAsString().compareTo(var.getObjectAsString()) <= 0;
                case INT:
                     return this.getObjectAsInt() <= var.getObjectAsDouble();
                case DOUBLE:
                     return this.getObjectAsDouble() <= var.getObjectAsDouble();
                case LIST:
                     if (size() != var.size()) {
                            return false;
                     }
                     if (!var.getType().equals(Var.Type.LIST)) {
                            return false;
                     }
                     int index = 0;
                     for (Var myVar : this.getObjectAsList()) {
                            if (!myVar.lessThanOrEqual(var.get(index))) {
                                 return false;
                            }
                     }
                     return true;
                case NULL:
                     return (var.getType() == Var.Type.NULL);
                default:
                     return false;
         }// end switch
    } // end lessThanOrEqual

    /**
     * Check to see if this var is greater than a given var.
     *
     * @param var the var to compare to.
     * @return true if this object is grater than the given var
     */
    public boolean greaterThan(Var var) {
         return var.lessThan(this);
    } // end greaterThan

    /**
     * Check to see if this var is greater than or equal to a given var
     *
     * @param var the var to compare to
     * @return true if this var is greater than or equal to the given var
     */
    public boolean greaterThanOrEqual(Var var) {
         return var.lessThanOrEqual(this);
    } // end greaterThanOrEqual

    /**
     * Compare this object's value to another
     *
     * @param val the object to compare to
     * @return the value 0 if this is equal to the argument; a value less than 0
     * if this is numerically less than the argument; and a value greater than 0
     * if this is numerically greater than the argument (signed comparison).
     */
    @Override
    public int compareTo(Object val) {
         // only instantiate if val is not instance of Var
         Var var;
         if (val instanceof Var) {
                var = (Var) val;
         } else {
                var = new Var(val);
         }
         switch (getType()) {
                case STRING:
                     return this.getObjectAsString().compareTo(var.getObjectAsString());
                case INT:
                     if (var.getType().equals(Var.Type.INT)) {
                            return ((Integer) this.getObjectAsInt()).compareTo(var.getObjectAsInt());
                     } else {
                            return ((Double) this.getObjectAsDouble()).compareTo(var.getObjectAsDouble());
                     }
                case DOUBLE:
                     return ((Double) this.getObjectAsDouble()).compareTo(var.getObjectAsDouble());
                case LIST:
                     // doesn't make sense
                     return Integer.MAX_VALUE;
                default:
                     // doesn't make sense
                     return Integer.MAX_VALUE;
         }// end switch
    } // end compareTo

    /**
     * Convert this Var to a string format.
     *
     * @return the string format of this var
     */
    @Override
    public String toString() {
         switch (getType()) {
                case STRING:
                     return getObject().toString();
                case INT:
                     Integer i = (Integer) getObject();
                     return i.toString();
                case DOUBLE:
                     Double d = (Double) _object;
                     return _formatter.format(d);
                case LIST:
                     List<Var> ll = (List) getObject();
                     StringBuilder sb = new StringBuilder();
                     boolean first = true;
                     for (Var v : ll) {
                            if (first) {
                                 first = false;
                                 sb.append("{");
                            } else {
                                 sb.append(", ");
                            }
                            sb.append(v.toString());
                     } // end for each Var
                     sb.append("}");
                     return sb.toString();
                case NULL:
                     return null;
                default:
                     return getObject().toString();
         }// end switch
    } // end toString

    /**
     * Internal method for inferring the "object type" of this object. When it
     * is done, it sets the private member value of _type. This will be
     * referenced later on when various method calls are made on this object.
     */
    private void inferType() {
         if (_object == null) {
             _type = Type.NULL;
         } else if (_object instanceof Var) {
            Var oldObj = (Var)_object;
            _type = oldObj.getType();
            _object = oldObj.getObject();
         } else if (_object instanceof String) {
                _type = Type.STRING;
         } else {
         // must be a number or a list
                // try to see if its a double
                try {
                     Double d = (Double) _object;
                     // it was a double, so keep going
                     _type = Type.DOUBLE;
                } catch (Exception ex) {
                     // not a double, see if it is an integer
                     try {
                            Integer i = (Integer) _object;
                            // it was an integer
                            _type = Type.INT;
                     } catch (Exception ex2) {
                            // not a double or integer, might be an array
                            if (_object instanceof List) {
                                 _type = Type.LIST;
                            } else if (_object instanceof List) {
                                 _type = Type.LIST;
                                 _object = new ArrayList((List) _object);
                            } else {
                                 _type = Type.UNKNOWN;
                            }
                     } // end not an integer
                } // end not a double
         } // end else not a string
    } // end inferType

    static double math_sum(Var myList) {
         double sum = 0;
         List<Var> ll = myList.getObjectAsList();
         for (Var var : ll) {
                sum += var.getObjectAsDouble();
         }
         return sum;
    }

    static double math_min(Var myList) {
         double min = Double.MAX_VALUE;
         double d;
         List<Var> ll = myList.getObjectAsList();
         for (Var var : ll) {
                d = var.getObjectAsDouble();
                if (d < min) {
                     min = d;
                }
         }
         return min;
    }

    static double math_max(Var myList) {
         double max = Double.MIN_VALUE;
         double d;
         List<Var> ll = myList.getObjectAsList();
         for (Var var : ll) {
                d = var.getObjectAsDouble();
                if (d > max) {
                     max = d;
                }
         }
         return max;
    }

    static double math_mean(Var myList) {
         return Var.math_sum(myList) / myList.size();
    }

    static double math_median(Var myList) {
         List<Var> ll = myList.getObjectAsList();
         Collections.sort(ll);
         int length = myList.size();
         int middle = length / 2;
         if (length % 2 == 1) {
                return ll.get(middle).getObjectAsDouble();
         } else {
                double d1 = ll.get(middle - 1).getObjectAsDouble();
                double d2 = ll.get(middle).getObjectAsDouble();
                return (d1 + d2) / 2.0;
         }
    }

    static Var math_modes(Var myList) {
         final Var modes = new Var();
         final Map<Double, Double> countMap = new HashMap<Double, Double>();
         double max = -1;
         double d;
         List<Var> ll = myList.getObjectAsList();
         for (Var var : ll) {
                d = var.getObjectAsDouble();
                double count = 0;
                if (countMap.containsKey(d)) {
                     count = countMap.get(d) + 1;
                } else {
                     count = 1;
                }
                countMap.put(d, count);
                if (count > max) {
                     max = count;
                }
         }
         for (final Map.Entry<Double, Double> tuple : countMap.entrySet()) {
                if (tuple.getValue() == max) {
                     modes.add(Var.valueOf(tuple.getKey().doubleValue()));
                }
         }
         return modes;
    }

    static double math_standard_deviation(Var myList) {
         double mean = math_mean(myList);
         double size = myList.size();
         double temp = 0;
         double d;
         List<Var> ll = myList.getObjectAsList();
         for (Var var : ll) {
                d = var.getObjectAsDouble();
                temp += (mean - d) * (mean - d);
         }
         double variance = temp / size;
         return Math.sqrt(variance);
    }

}

}
