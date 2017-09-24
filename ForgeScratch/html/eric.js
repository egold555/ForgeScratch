goog.require('Blockly.Blocks');



//Blockly.Blocks.colour.HUE = 20;

/*

Blockly.Blocks['name'] = {
  
  init: function() {
    this.jsonInit({
      
    });
  }
};


*/

/*
  NI: Not implemented
  NFI: Not fully implemented
  NT: Not tested
*/

function showError(block, msg){
  var nice = msg.replace("FS ", "");
  block.setWarningText(nice);
  throw("FS " + msg); //code.js will catch this and forward msg on to Java
};

var RETURNS = '/*returns*/';


Blockly.Blocks['mcblock'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblock",
  "message0": "Minecraft Block %1 Name: %2 %3 Properties: %4 %5 Unbreakable %6 %7 Explosion Resistant %8 %9 Options %10 %11",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_input",
      "name": "NAME",
      "text": "change_me"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_dropdown",
      "name": "MATERIAL",
      "options": [
        [
          "Dirt",
          "Material.ground"
        ],
        [
          "Stone",
          "Material.rock"
        ],
        [
          "Wood",
          "Material.wood"
        ],
        [
          "Glass",
          "Material.glass"
        ]
      ]
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "UNBREAKABLE",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "EXPLOSION",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "Options",
      "check": "mcblockoptions"
    }
  ],
  "inputsInline": false,
  "colour": 315,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

function isJavaId(c)
{
  return c.match(/[a-zA-Z0-9]/i);
}

function make_java_id(name)
{
  var result = "";
  for (var i = 0; i < name.length; ++i) {
    var c = name.charAt(i);
    if (isJavaId(c)) {
      result = result + c;
    }
    else {
      result = result + "_";
    }
  }

  return result;
}


Blockly.Java['mcblock'] = function(block) {
  var value_name = make_java_id(block.getFieldValue('NAME'));
  var raw_value_name = block.getFieldValue('NAME');
  var dropdown_material = block.getFieldValue('MATERIAL');
  var checkbox_unbreakable = block.getFieldValue('UNBREAKABLE') == 'TRUE';
  var checkbox_explosion = block.getFieldValue('EXPLOSION') == 'TRUE';
  var statements_options = Blockly.Java.statementToCode(block, 'Options');
  var code = 
    '    public class Mcblock_' + value_name + ' extends BlockBase {\n' +
    '        public Mcblock_' + value_name + '() {\n' +
    '            super(BLOCK_ID, CREATIVE_TAB, "' + raw_value_name + '", ' + dropdown_material + '); \n' +
    '\n'+
    'if(' + checkbox_unbreakable + '){\n' +
    '    setHardness(-1.0F);\n' +
    '}\n' +
    
    'if(' + checkbox_explosion + '){\n' +
    '    setResistance(6000000.0F);\n' +
    '}\n' +

    '        }\n\n' +
          statements_options +
    '    }\n';

  return code;
};



Blockly.Blocks['mcblockflower'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockflower",
  "message0": "Minecraft Flower %1 Name: %2 %3 Unbreakable %4 %5 Explosion Resistant %6 %7 Options %8 %9",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_input",
      "name": "NAME",
      "text": "change_me"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "UNBREAKABLE",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "EXPLOSION",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "Options",
      "check": "mcblockoptions"
    }
  ],
  "inputsInline": false,
  "colour": 315,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};



Blockly.Java['mcblockflower'] = function(block) {
  var value_name = make_java_id(block.getFieldValue('NAME'));
  var raw_value_name = block.getFieldValue('NAME');
  var checkbox_unbreakable = block.getFieldValue('UNBREAKABLE') == 'TRUE';
  var checkbox_explosion = block.getFieldValue('EXPLOSION') == 'TRUE';
  var statements_options = Blockly.Java.statementToCode(block, 'Options');

  var code = 
    '    public class McblockFlower_' + value_name + ' extends BlockBaseFlower {\n' +
    '        public McblockFlower_' + value_name + '() {\n' +
    '            super(BLOCK_ID, CREATIVE_TAB, "' + raw_value_name + '"); \n' +
    '\n'+
    'if(' + checkbox_unbreakable + '){\n' +
    '    setHardness(-1.0F);\n' +
    '}\n' +
    
    'if(' + checkbox_explosion + '){\n' +
    '    setResistance(6000000.0F);\n' +
    '}\n' +

    '        }\n\n' +
          statements_options +
    '    }\n';
  return code;
};




Blockly.Blocks['mcblockplant'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockplant",
  "message0": "Minecraft Plant %1 Name: %2 %3 Plant Type %4 %5 Unbreakable %6 %7 Explosion Resistant %8 %9 World Generation %10 %11 Needs water to generate %12 %13 Plant Max Grow Height: %14 %15 Options %16 %17",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_input",
      "name": "NAME",
      "text": "change_me"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_dropdown",
      "name": "TYPE",
      "options": [
        [
          "Plains",
          "EnumPlantType.Plains"
        ],
        [
          "Desert",
          "EnumPlantType.Desert"
        ],
        [
          "Beach",
          "EnumPlantType.Beach"
        ],
        [
          "Cave",
          "EnumPlantType.Cave"
        ],
        [
          "Water",
          "EnumPlantType.Water"
        ],
        [
          "Crop",
          "EnumPlantType.Crop"
        ]
      ]
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "UNBREAKABLE",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "EXPLOSION",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "GEN",
      "checked": true
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "WATERGEN",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_input",
      "name": "HEIGHT",
      "text": "3"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "Options",
      "check": "mcblockoptions"
    }
  ],
  "inputsInline": false,
  "colour": 315,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcblockplant'] = function(block) {
  var value_name = make_java_id(block.getFieldValue('NAME'));
  var raw_value_name = block.getFieldValue('NAME');
  var dropdown_type = block.getFieldValue('TYPE');
  var checkbox_unbreakable = block.getFieldValue('UNBREAKABLE') == 'TRUE';
  var checkbox_explosion = block.getFieldValue('EXPLOSION') == 'TRUE';
  var checkbox_gen = block.getFieldValue('GEN') == 'TRUE';
  var checkbox_watergen = block.getFieldValue('WATERGEN') == 'TRUE';
  var value_height = block.getFieldValue('HEIGHT');
  var statements_options = Blockly.Java.statementToCode(block, 'Options');

  if(isNaN(parseInt(value_height))){
    showError(block, "Max Plant Grow Height must be a number!");
  }

  var code = 
    '    public class McblockPlant_' + value_name + ' extends BlockBasePlant {\n' +
    '        public McblockPlant_' + value_name + '() {\n' +
    '            super(BLOCK_ID, CREATIVE_TAB, "' + raw_value_name + '", ' + dropdown_type + ', ' + checkbox_gen + ', ' + checkbox_watergen + ', ' + value_height + '); \n' +
    '\n'+
    'if(' + checkbox_unbreakable + '){\n' +
    '    setHardness(-1.0F);\n' +
    '}\n' +
    
    'if(' + checkbox_explosion + '){\n' +
    '    setResistance(6000000.0F);\n' +
    '}\n' +

    '        }\n\n' +
          statements_options +
    '    }\n';
  return code;
};



Blockly.Blocks['mcblockoptions_quantity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Amount: %1",
  "args0": [
    {
      "type": "input_value",
      "name": "AMOUNT",
      "check": "Number"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcblockoptions_quantity'] = function(block) {
  var value_amount = Blockly.Java.valueToCode(block, 'AMOUNT', Blockly.Java.ORDER_ATOMIC);
  var code = 
  '    @Override\n' + 
  '    public int quantityDropped(Random r){\n' +
  '        return Math.max(0,(int)' + value_amount + ');\n' + 
  '    }\n';
  return code;
};



Blockly.Blocks['mcblockoptions_lightopacity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Light Opacity %1",
  "args0": [
    {
      "type": "input_value",
      "name": "LIGHT_OPACITY",
      "check": "Number"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcblockoptions_lightopacity'] = function(block) {
  var value_light_opacity = Blockly.Java.valueToCode(block, 'LIGHT_OPACITY', Blockly.Java.ORDER_ATOMIC);
  
  var code = 
  '    @Override\n' +
  '    public int getLightOpacity() {\n' +
  '        return Math.min(15, Math.max(0,(int)' + value_light_opacity + '));\n' +
  '    }\n';

  return code;
};



Blockly.Blocks['mcblockoptions_lightvalue'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Light Value %1",
  "args0": [
    {
      "type": "input_value",
      "name": "LIGHT_VALUE",
      "check": "Number"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Java['mcblockoptions_lightvalue'] = function(block) {
  var value_light_value  = Blockly.Java.valueToCode(block, 'LIGHT_VALUE', Blockly.Java.ORDER_ATOMIC);
  
  var code = 
  '    @Override\n' +
  '    public int getLightValue() {\n' +
  '        return Math.min(15, Math.max(0,(int)' + value_light_value  + '));\n' +
  '    }\n';

  return code;
};


Blockly.Blocks['mcblockoptions_click_right'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions_click_right",
  "message0": "On Right Click %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE",
      "check": "action"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Java['mcblockoptions_click_right'] = function(block) {
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');
  
  statements_code = statements_code.replace(RETURNS, 'true');

  var code = 
  '    @Override\n' +
  '    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {\n' +
  '         EntityLiving entity = null;\n' +
  '        ' + statements_code + '\n' +
  '        return true;\n' +
  '    }\n';
  return code;
};


Blockly.Blocks['mcblockoptions_click_left'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions_click_left",
  "message0": "On Left Click %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE",
      "check": "action"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Java['mcblockoptions_click_left'] = function(block) {
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');
  statements_code = statements_code.replace(RETURNS, '');
  var code = 
  '    @Override\n' +
  '    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {\n' +
'         EntityLiving entity = null;\n' +
  '        ' + statements_code + '\n' +
  '    }\n';
  return code;
};


Blockly.Blocks['mcblockoptions_blockplaced'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions_blockplaced",
  "message0": "On Block Placed %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE",
      "check": "action"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Java['mcblockoptions_blockplaced'] = function(block) {
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');
  statements_code = statements_code.replace(RETURNS, '');

  var code = 
  '    @Override\n' +
  '    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {\n' +
  '         EntityLiving entity = null;\n' +
  '        ' + statements_code + '\n' +
  '    }\n';
  return code;
};



Blockly.Blocks['mcblockoptions_block_broken_player'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions_block_broken_player",
  "message0": "On Block Mined %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE",
      "check": "action"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Java['mcblockoptions_block_broken_player'] = function(block) {
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');
  statements_code = statements_code.replace(RETURNS, '');

  var code = 
  '    @Override\n' +
  '    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {\n' +
  '         EntityLiving entity = null;\n' +
  '        ' + statements_code + '\n' +
  '    }\n';
  return code;
};



Blockly.Blocks['mcblockoptions_block_broken_explosion'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions_block_broken_explosion",
  "message0": "On Block Destroyed By Explosion %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "NAME",
      "check": "action"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Java['mcblockoptions_block_broken_explosion'] = function(block) {
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');
  statements_code = statements_code.replace(RETURNS, '');

  var code = 
  '    @Override\n' +
  '    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {\n' +
  '         EntityPlayer player = null;\n' +
  '         EntityLiving entity = null;\n' +
  '        ' + statements_code + '\n' +
  '    }\n';
  return code;
};



Blockly.Blocks['mcblockoptions_walkthrough'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "On Block Walkthough %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcblockoptions_walkthrough'] = function(block) {
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');
  statements_code = statements_code.replace(RETURNS, '');

  var code = 
  '    @Override\n' +
  '    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity genericEntity) {\n' +
  '        EntityPlayer player = null;\n' +
  '        EntityLiving entity = null;\n' +
  '        if(genericEntity instanceof EntityPlayer){\n' +
  '            player = (EntityPlayer)entity;\n' + 
  '        }\n' +
    '      else if(genericEntity instanceof EntityLiving){\n' +
  '            player = (EntityPlayer)entity;\n' + 
  '        }\n' +
  '            ' + statements_code + '\n' +
  '    }\n' +
  '\n' +
  '    @Override\n' +
  '    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_getCollisionBoundingBoxFromPool_1_, int p_getCollisionBoundingBoxFromPool_2_, int p_getCollisionBoundingBoxFromPool_3_, int p_getCollisionBoundingBoxFromPool_4_){return null;}\n' +
  '\n' +
  '    @Override\n' +
  '    public boolean renderAsNormalBlock(){return false;}\n'

  ;
  return code;
}; 


Blockly.Blocks['mcblockoptions_transparent'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Render Block Like Glass",
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Java['mcblockoptions_transparent'] = function(block) {
  var code = 
  '    @Override\n' +
  '    public boolean isOpaqueCube(){return false;}\n';
  return code;
};



Blockly.Blocks['mcblockoptions_experience'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockoptions",
  "message0": "Amount Of XP To Drop %1",
  "args0": [
    {
      "type": "input_value",
      "name": "AMOUNT",
      "check": "Number"
    }
  ],
  "previousStatement": "mcblockoptions",
  "nextStatement": "mcblockoptions",
  "colour": 330,
  "tooltip": "",
  "helpUrl": ""
    });
  }
}; 

Blockly.Java['mcblockoptions_experience'] = function(block) {
  var value_light_value  = Blockly.Java.valueToCode(block, 'AMOUNT', Blockly.Java.ORDER_ATOMIC);
  
  var code = 
  '    @Override\n' +
  '    public int getExpDrop() {\n' +
  '        return Math.max(0,(int)' + value_light_value  + ');\n' +
  '    }\n';

  return code;
};


Blockly.Blocks['mcaction_time_selector'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_time_selector",
  "message0": "Set time to %1",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "time",
      "options": [
        [
          "Sunrise",
          "0"
        ],
        [
          "Day",
          "1000"
        ],
        [
          "Afternoon",
          "6000"
        ],
        [
          "Sunset",
          "12000"
        ],
        [
          "Night",
          "13000"
        ],
        [
          "Midnight",
          "18000"
        ]
      ]
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_time_selector'] = function(block) {
  var dropdown_time = block.getFieldValue('time');
  
  var code = 'if(!world.isRemote){world.setWorldTime(Math.max(0, (long)' + dropdown_time + '));}';

  return code;
};


Blockly.Blocks['mcaction_time_raw'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_time_raw",
  "message0": "Set time to %1",
  "args0": [
    {
      "type": "input_value",
      "name": "time",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_time_raw'] = function(block) {
  var value_time = Blockly.Java.valueToCode(block, 'time', Blockly.Java.ORDER_ATOMIC);
  
  var code = 'if(!world.isRemote){world.setWorldTime(Math.max(0, (long)' + value_time + '));}\n';

  return code;
};


Blockly.Blocks['mcaction_spawn_mob'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_spawn_mob",
  "message0": "Spawn Mob %1 Mob:  %2 %3 Location X: %4 Location Y: %5 Location Z: %6",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_dropdown",
      "name": "MOB",
      "options": [
        [
          "Creeper",
          "Creeper"
        ],
        [
          "Skeleton",
          "Skeleton"
        ],
        [
          "Spider",
          "Spider"
        ],
        [
          "Giant",
          "Giant"
        ],
        [
          "Zombie",
          "Zombie"
        ],
        [
          "Slime",
          "Slime"
        ],
        [
          "Ghast",
          "Ghast"
        ],
        [
          "Zombie Pigman",
          "PigZombie"
        ],
        [
          "Enderman",
          "Enderman"
        ],
        [
          "Cave Spider",
          "CaveSpider"
        ],
        [
          "Silverfish",
          "Silverfish"
        ],
        [
          "Blaze",
          "Blaze"
        ],
        [
          " Lava Slime",
          "LavaSlime"
        ],
        [
          "Ender Dragon",
          "EnderDragon"
        ],
        [
          " Wither",
          "WitherBoss"
        ],
        [
          "Bat",
          "Bat"
        ],
        [
          "Pig",
          "Pig"
        ],
        [
          "Sheep",
          "Sheep"
        ],
        [
          "Cow",
          "Cow"
        ],
        [
          "Chicken",
          "Chicken"
        ],
        [
          "Squid",
          "Squid"
        ],
        [
          "Wolf",
          "Wolf"
        ],
        [
          "Mooshroom",
          "Mooshroom"
        ],
        [
          " Snow Man",
          "SnowMan"
        ],
        [
          "Cat",
          "Ozelot"
        ],
        [
          "Iron Golem",
          "VillagerGolem"
        ],
        [
          "Horse",
          "EntityHorse"
        ],
        [
          "Villager",
          "Villager"
        ]
      ]
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_spawn_mob'] = function(block) {
  var dropdown_mob = block.getFieldValue('MOB');
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);

  var code = 'if(!world.isRemote){entity = ModHelpers.spawnEntityInWorld(world, ' + value_loc_x + ', ' + value_loc_y + ', ' + value_loc_z + ', "' + dropdown_mob + '");}\n';
  return code;
};

Blockly.Blocks['mcaction_explosion'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_explosion",
  "message0": "Explosion %1 Power %2 Location X %3 Location Y %4 Location Z %5 Smoke %6 %7 Fire %8",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "POWER",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    },
    {
      "type": "field_checkbox",
      "name": "SMOKE",
      "checked": true
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "FIRE",
      "checked": false
    }
  ],
  "inputsInline": false,
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mcaction_explosion'] = function(block) {
  var value_power = Blockly.Java.valueToCode(block, 'POWER', Blockly.Java.ORDER_ATOMIC);
  var checkbox_smoke = block.getFieldValue('SMOKE') == 'TRUE';
  var checkbox_fire = block.getFieldValue('FIRE') == 'TRUE';
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);

  var code = 'if(!world.isRemote){world.newExplosion((Entity)null, ' + value_loc_x + ' + 0.5f, ' + value_loc_y + ' + 0.5f, ' + value_loc_z + ' + 0.5f, ' + value_power + ', ' + checkbox_fire + ', ' + checkbox_smoke + ');}\n';
  return code;
};

Blockly.Blocks['mcaction_potionplayer'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_potionplayer",
  "message0": "Add Potion Effect To Player %1 Potion:  %2 %3 Seconds %4 Amplifier %5 Make Particles Invisible %6",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_dropdown",
      "name": "POTION",
      "options": [
        [
          "Speed",
          "1"
        ],
        [
          "Slowness",
          "2"
        ],
        [
          "Haste",
          "3"
        ],
        [
          "Mining Fatigue",
          "4"
        ],
        [
          "Strength",
          "5"
        ],
        [
          "Instant Health",
          "6"
        ],
        [
          "Instant Damage",
          "7"
        ],
        [
          "Jump Boost",
          "8"
        ],
        [
          "Nausea",
          "9"
        ],
        [
          "Regeneration",
          "10"
        ],
        [
          "Resistance",
          "11"
        ],
        [
          "Fire Resistance",
          "12"
        ],
        [
          "Water Breathing",
          "13"
        ],
        [
          "Invisibility",
          "14"
        ],
        [
          "Blindness",
          "15"
        ],
        [
          "Night Vision",
          "16"
        ],
        [
          "Hunger",
          "17"
        ],
        [
          "Weakness",
          "18"
        ],
        [
          "Poison",
          "19"
        ],
        [
          "Wither",
          "20"
        ],
        [
          "Health Boost",
          "21"
        ],
        [
          "Absorption",
          "22"
        ],
        [
          "Saturation",
          "23"
        ]
      ]
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "TIME",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "AMP",
      "check": "Number"
    },
    {
      "type": "field_checkbox",
      "name": "INVIS",
      "checked": true
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_potionplayer'] = function(block) {
  var dropdown_potion = block.getFieldValue('POTION');
  var value_time = Blockly.Java.valueToCode(block, 'TIME', Blockly.Java.ORDER_ATOMIC);
  var value_amp = Blockly.Java.valueToCode(block, 'AMP', Blockly.Java.ORDER_ATOMIC);
  var checkbox_invis = block.getFieldValue('INVIS') == 'TRUE';

  var code = 'if(!world.isRemote){if(player != null){ModHelpers.addPotionToEntity(player, ' + dropdown_potion + ', ' + value_time +', ' + value_amp +', ' + checkbox_invis +');}}\n';
  return code;
};

Blockly.Blocks['mcaction_potionentity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_potionentity",
  "message0": "Add Potion Effect To A Mob %1 Potion:  %2 %3 Seconds %4 Amplifier %5 Make Particles Invisible %6",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_dropdown",
      "name": "POTION",
      "options": [
        [
          "Speed",
          "1"
        ],
        [
          "Slowness",
          "2"
        ],
        [
          "Haste",
          "3"
        ],
        [
          "Mining Fatigue",
          "4"
        ],
        [
          "Strength",
          "5"
        ],
        [
          "Instant Health",
          "6"
        ],
        [
          "Instant Damage",
          "7"
        ],
        [
          "Jump Boost",
          "8"
        ],
        [
          "Nausea",
          "9"
        ],
        [
          "Regeneration",
          "10"
        ],
        [
          "Resistance",
          "11"
        ],
        [
          "Fire Resistance",
          "12"
        ],
        [
          "Water Breathing",
          "13"
        ],
        [
          "Invisibility",
          "14"
        ],
        [
          "Blindness",
          "15"
        ],
        [
          "Night Vision",
          "16"
        ],
        [
          "Hunger",
          "17"
        ],
        [
          "Weakness",
          "18"
        ],
        [
          "Poison",
          "19"
        ],
        [
          "Wither",
          "20"
        ],
        [
          "Health Boost",
          "21"
        ],
        [
          "Absorption",
          "22"
        ],
        [
          "Saturation",
          "23"
        ]
      ]
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "TIME",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "AMP",
      "check": "Number"
    },
    {
      "type": "field_checkbox",
      "name": "INVIS",
      "checked": true
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_potionentity'] = function(block) {
  var dropdown_potion = block.getFieldValue('POTION');
  var value_time = Blockly.Java.valueToCode(block, 'TIME', Blockly.Java.ORDER_ATOMIC);
  var value_amp = Blockly.Java.valueToCode(block, 'AMP', Blockly.Java.ORDER_ATOMIC);
  var checkbox_invis = block.getFieldValue('INVIS') == 'TRUE';

  var code = 'if(!world.isRemote){if(entity != null) {ModHelpers.addPotionToEntity(entity, ' + dropdown_potion + ', ' + value_time +', ' + value_amp +', ' + checkbox_invis +');}}\n';
  return code;
};


Blockly.Blocks['mcaction_playsound'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_playsound",
  "message0": "Play Sound:  %1",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "SOUND",
      "options": [
        ["ambient.cave", "ambient.cave"], 
        ["ambient.weather.rain", "ambient.weather.rain"], 
        ["ambient.weather.thunder", "ambient.weather.thunder"],
        ["damage.fallbig", "damage.fallbig"],
        ["damage.fallsmall", "damage.fallsmall"],
        ["damage.hit", "damage.hit"],
        ["dig.cloth", "dig.cloth"],
        ["dig.grass", "dig.grass"],
        ["dig.gravel", "dig.gravel"],
        ["dig.sand", "dig.sand"],
        ["dig.snow", "dig.snow"],
        ["dig.stone", "dig.stone"],
        ["dig.wood", "dig.wood"],
        ["fire.fire", "fire.fire"],
        ["fire.ignite", "fire.ignite"],
        ["fireworks.blast_far1", "fireworks.blast_far1"],
        ["fireworks.blast1", "fireworks.blast1"],
        ["fireworks.largeBlast_far1", "fireworks.largeBlast_far1"],
        ["fireworks.largeBlast1", "fireworks.largeBlast1"],
        ["fireworks.launch1", "fireworks.launch1"],
        ["fireworks.twinkle_far1", "fireworks.twinkle_far1"],
        ["fireworks.twinkle1", "fireworks.twinkle1"],
        ["liquid.lava", "liquid.lava"],
        ["liquid.lavapop", "liquid.lavapop"],
        ["liquid.splash", "liquid.splash"],
        ["liquid.swim", "liquid.swim"],
        ["liquid.water", "liquid.water"],
        ["minecart.base", "minecart.base"],
        ["minecart.inside", "minecart.inside"],
        ["mob.bat.death", "mob.bat.death"],
        ["mob.bat.hurt", "mob.bat.hurt"],
        ["mob.bat.idle", "mob.bat.idle"],
        ["mob.bat.loop", "mob.bat.loop"],
        ["mob.bat.takeoff", "mob.bat.takeoff"],
        ["mob.blaze.breathe", "mob.blaze.breathe"],
        ["mob.blaze.death", "mob.blaze.death"],
        ["mob.blaze.hit", "mob.blaze.hit"],
        ["mob.cat.hiss", "mob.cat.hiss"],
        ["mob.cat.hitt", "mob.cat.hitt"],
        ["mob.cat.meow", "mob.cat.meow"],
        ["mob.cat.purr", "mob.cat.purr"],
        ["mob.cat.purreow", "mob.cat.purreow"],
        ["mob.chicken.hurt", "mob.chicken.hurt"],
        ["mob.chicken.plop", "mob.chicken.plop"],
        ["mob.chicken.say", "mob.chicken.say"],
        ["mob.chicken.step", "mob.chicken.step"],
        ["mob.cow.hurt", "mob.cow.hurt"],
        ["mob.cow.say", "mob.cow.say"],
        ["mob.cow.step", "mob.cow.step"],
        ["mob.creeper.death", "mob.creeper.death"],
        ["mob.creeper.say", "mob.creeper.say"],
        ["mob.enderdragon.end", "mob.enderdragon.end"],
        ["mob.enderdragon.growl", "mob.enderdragon.growl"],
        ["mob.enderdragon.hit", "mob.enderdragon.hit"],
        ["mob.enderdragon.wings", "mob.enderdragon.wings"],
        ["mob.endermen.death", "mob.endermen.death"],
        ["mob.endermen.hit", "mob.endermen.hit"],
        ["mob.endermen.idle", "mob.endermen.idle"],
        ["mob.endermen.portal", "mob.endermen.portal"],
        ["mob.endermen.scream", "mob.endermen.scream"],
        ["mob.endermen.stare", "mob.endermen.stare"],
        ["mob.ghast.affectionate_scream", "mob.ghast.affectionate_scream"],
        ["mob.ghast.charge", "mob.ghast.charge"],
        ["mob.ghast.death", "mob.ghast.death"],
        ["mob.ghast.fireball4", "mob.ghast.fireball4"],
        ["mob.ghast.moan", "mob.ghast.moan"],
        ["mob.ghast.scream", "mob.ghast.scream"],
        ["mob.irongolem.death", "mob.irongolem.death"],
        ["mob.irongolem.hit", "mob.irongolem.hit"],
        ["mob.irongolem.throw", "mob.irongolem.throw"],
        ["mob.irongolem.walk", "mob.irongolem.walk"],
        ["mob.magmacube.big", "mob.magmacube.big"],
        ["mob.magmacube.jump", "mob.magmacube.jump"],
        ["mob.magmacube.small", "mob.magmacube.small"],
        ["mob.pig.death", "mob.pig.death"],
        ["mob.pig.say", "mob.pig.say"],
        ["mob.pig.step", "mob.pig.step"],
        ["mob.sheep.say", "mob.sheep.say"],
        ["mob.sheep.shear", "mob.sheep.shear"],
        ["mob.sheep.step", "mob.sheep.step"],
        ["mob.silverfish.hit", "mob.silverfish.hit"],
        ["mob.silverfish.kill", "mob.silverfish.kill"],
        ["mob.silverfish.say", "mob.silverfish.say"],
        ["mob.silverfish.step", "mob.silverfish.step"],
        ["mob.skeleton.death", "mob.skeleton.death"],
        ["mob.skeleton.hurt", "mob.skeleton.hurt"],
        ["mob.skeleton.say", "mob.skeleton.say"],
        ["mob.skeleton.step", "mob.skeleton.step"],
        ["mob.slime.attack", "mob.slime.attack"],
        ["mob.slime.big", "mob.slime.big"],
        ["mob.slime.small", "mob.slime.small"],
        ["mob.spider.death", "mob.spider.death"],
        ["mob.spider.say", "mob.spider.say"],
        ["mob.spider.step", "mob.spider.step"],
        ["mob.villager.death", "mob.villager.death"],
        ["mob.villager.haggle", "mob.villager.haggle"],
        ["mob.villager.hit", "mob.villager.hit"],
        ["mob.villager.idle", "mob.villager.idle"],
        ["mob.villager.no", "mob.villager.no"],
        ["mob.villager.yes", "mob.villager.yes"],
        ["mob.wither.death", "mob.wither.death"],
        ["mob.wither.hurt", "mob.wither.hurt"],
        ["mob.wither.idle", "mob.wither.idle"],
        ["mob.wither.shoot", "mob.wither.shoot"],
        ["mob.wither.spawn", "mob.wither.spawn"],
        ["mob.wolf.bark", "mob.wolf.bark"],
        ["mob.wolf.death", "mob.wolf.death"],
        ["mob.wolf.growl", "mob.wolf.growl"],
        ["mob.wolf.howl", "mob.wolf.howl"],
        ["mob.wolf.hurt", "mob.wolf.hurt"],
        ["mob.wolf.panting", "mob.wolf.panting"],
        ["mob.wolf.shake", "mob.wolf.shake"],
        ["mob.wolf.step", "mob.wolf.step"],
        ["mob.wolf.whine", "mob.wolf.whine"],
        ["mob.zombie.death", "mob.zombie.death"],
        ["mob.zombie.hurt", "mob.zombie.hurt"],
        ["mob.zombie.infect", "mob.zombie.infect"],
        ["mob.zombie.metal", "mob.zombie.metal"],
        ["mob.zombie.say", "mob.zombie.say"],
        ["mob.zombie.step", "mob.zombie.step"],
        ["mob.zombie.remedy", "mob.zombie.remedy"],
        ["mob.zombie.unfect", "mob.zombie.unfect"],
        ["mob.zombie.wood", "mob.zombie.wood"],
        ["mob.zombie.woodbreak", "mob.zombie.woodbreak"],
        ["mob.zombiepig.zpig", "mob.zombiepig.zpig"],
        ["mob.zombiepig.zpigangry", "mob.zombiepig.zpigangry"],
        ["mob.zombiepig.zpigdeath", "mob.zombiepig.zpigdeath"],
        ["mob.zombiepig.zpighurt", "mob.zombiepig.zpighurt"],
        ["note.bass", "note.bass"],
        ["note.bassattack", "note.bassattack"],
        ["note.bd", "note.bd"],
        ["note.harp", "note.harp"],
        ["note.hat", "note.hat"],
        ["note.pling", "note.pling"],
        ["note.snare", "note.snare"],
        ["portal.portal", "portal.portal"],
        ["portal.travel", "portal.travel"],
        ["portal.trigger", "portal.trigger"],
        ["random.anvil_break", "random.anvil_break"],
        ["random.anvil_land", "random.anvil_land"],
        ["random.anvil_use", "random.anvil_use"],
        ["random.bow", "random.bow"],
        ["random.bowhit", "random.bowhit"],
        ["random.break", "random.break"],
        ["random.breath", "random.breath"],
        ["random.burp", "random.burp"],
        ["random.chestclosed", "random.chestclosed"],
        ["random.chestopen", "random.chestopen"],
        ["random.classic_hurt", "random.classic_hurt"],
        ["random.click", "random.click"],
        ["random.door_close", "random.door_close"],
        ["random.door_open", "random.door_open"],
        ["random.drink", "random.drink"],
        ["random.eat", "random.eat"],
        ["random.explode", "random.explode"],
        ["random.fizz", "random.fizz"],
        ["random.fuse", "random.fuse"],
        ["random.glass", "random.glass"],
        ["random.levelup", "random.levelup"],
        ["random.orb", "random.orb"],
        ["random.pop", "random.pop"],
        ["random.successful_hit", "random.successful_hit"],
        ["random.wood_click", "random.wood_click"],
        ["step.cloth", "step.cloth"],
        ["step.grass", "step.grass"],
        ["step.gravel", "step.gravel"],
        ["step.ladder", "step.ladder"],
        ["step.sand", "step.sand"],
        ["step.snow", "step.snow"],
        ["step.stone", "step.stone"],
        ["step.wood", "step.wood"],
        ["tile.piston.in", "tile.piston.in"],
        ["tile.piston.out", "tile.piston.out"]
      ]
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_playsound'] = function(block) {
  var dropdown_sound = block.getFieldValue('SOUND');

  var code = 'if(player != null){world.playSoundAtEntity(player, "' + dropdown_sound + '", 1, 1);}\n';
  return code;
};



Blockly.Blocks['mciteminput'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mciteminput",
  "message0": "Item %1",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "ITEM",
      "options": [
            ["Apple", "apple"],
            ["Arrow", "arrow"],
            ["Baked Potato", "baked_potato"],
            ["Bed", "bed"],
            ["Beef", "beef"],
            ["Blaze Powder", "blaze_powder"],
            ["Blaze Rod", "blaze_rod"],
            ["Boat", "boat"],
            ["Bone", "bone"],
            ["Book", "book"],
            ["Bow", "bow"],
            ["Bowl", "bowl"],
            ["Bread", "bread"],
            ["Brewing Stand", "brewing_stand"],
            ["Brick", "brick"],
            ["Bucket", "bucket"],
            ["Cake", "cake"],
            ["Carrot", "carrot"],
            ["Carrot On A Stick", "carrot_on_a_stick"],
            ["Cauldron", "cauldron"],
            ["Chainmail Boots", "chainmail_boots"],
            ["Chainmail Chestplate", "chainmail_chestplate"],
            ["Chainmail Helmet", "chainmail_helmet"],
            ["Chainmail Leggings", "chainmail_leggings"],
            ["Chest Minecart", "chest_minecart"],
            ["Chicken", "chicken"],
            ["Clay Ball", "clay_ball"],
            ["Clock", "clock"],
            ["Coal", "coal"],
            ["Command Block Minecart", "command_block_minecart"],
            ["Comparator", "comparator"],
            ["Compass", "compass"],
            ["Cooked Beef", "cooked_beef"],
            ["Cooked Chicken", "cooked_chicken"],
            ["Cooked Fished", "cooked_fished"],
            ["Cooked Porkchop", "cooked_porkchop"],
            ["Cookie", "cookie"],
            ["Diamond", "diamond"],
            ["Diamond Axe", "diamond_axe"],
            ["Diamond Boots", "diamond_boots"],
            ["Diamond Chestplate", "diamond_chestplate"],
            ["Diamond Helmet", "diamond_helmet"],
            ["Diamond Hoe", "diamond_hoe"],
            ["Diamond Horse Armor", "diamond_horse_armor"],
            ["Diamond Leggings", "diamond_leggings"],
            ["Diamond Pickaxe", "diamond_pickaxe"],
            ["Diamond Shovel", "diamond_shovel"],
            ["Diamond Sword", "diamond_sword"],
            ["Dye", "dye"],
            ["Egg", "egg"],
            ["Emerald", "emerald"],
            ["Enchanted Book", "enchanted_book"],
            ["Ender Eye", "ender_eye"],
            ["Ender Pearl", "ender_pearl"],
            ["Experience Bottle", "experience_bottle"],
            ["Feather", "feather"],
            ["Fermented Spider Eye", "fermented_spider_eye"],
            ["Fire Charge", "fire_charge"],
            ["Firework Charge", "firework_charge"],
            ["Fireworks", "fireworks"],
            ["Fish", "fish"],
            ["Fishing Rod", "fishing_rod"],
            ["Flint", "flint"],
            ["Flint And Steel", "flint_and_steel"],
            ["Flower Pot", "flower_pot"],
            ["Furnace Minecart", "furnace_minecart"],
            ["Ghast Tear", "ghast_tear"],
            ["Glass Bottle", "glass_bottle"],
            ["Glowstone Dust", "glowstone_dust"],
            ["Gold Ingot", "gold_ingot"],
            ["Gold Nugget", "gold_nugget"],
            ["Golden Apple", "golden_apple"],
            ["Golden Axe", "golden_axe"],
            ["Golden Boots", "golden_boots"],
            ["Golden Carrot", "golden_carrot"],
            ["Golden Chestplate", "golden_chestplate"],
            ["Golden Helmet", "golden_helmet"],
            ["Golden Hoe", "golden_hoe"],
            ["Golden Horse Armor", "golden_horse_armor"],
            ["Golden Leggings", "golden_leggings"],
            ["Golden Pickaxe", "golden_pickaxe"],
            ["Golden Shovel", "golden_shovel"],
            ["Golden Sword", "golden_sword"],
            ["Gunpowder", "gunpowder"],
            ["Hopper Minecart", "hopper_minecart"],
            ["Iron Axe", "iron_axe"],
            ["Iron Boots", "iron_boots"],
            ["Iron Chestplate", "iron_chestplate"],
            ["Iron Door", "iron_door"],
            ["Iron Helmet", "iron_helmet"],
            ["Iron Hoe", "iron_hoe"],
            ["Iron Horse Armor", "iron_horse_armor"],
            ["Iron Ingot", "iron_ingot"],
            ["Iron Leggings", "iron_leggings"],
            ["Iron Pickaxe", "iron_pickaxe"],
            ["Iron Shovel", "iron_shovel"],
            ["Iron Sword", "iron_sword"],
            ["Item Frame", "item_frame"],
            ["ItemMapfilled Map", "ItemMapfilled_map"],
            ["ItemShearsshears", "ItemShearsshears"],
            ["Lava Bucket", "lava_bucket"],
            ["Lead", "lead"],
            ["Leather", "leather"],
            ["Leather Boots", "leather_boots"],
            ["Leather Chestplate", "leather_chestplate"],
            ["Leather Helmet", "leather_helmet"],
            ["Leather Leggings", "leather_leggings"],
            ["Magma Cream", "magma_cream"],
            ["Map", "map"],
            ["Melon", "melon"],
            ["Melon Seeds", "melon_seeds"],
            ["Milk Bucket", "milk_bucket"],
            ["Minecart", "minecart"],
            ["Mushroom Stew", "mushroom_stew"],
            ["Name Tag", "name_tag"],
            ["Nether Star", "nether_star"],
            ["Nether Wart", "nether_wart"],
            ["Netherbrick", "netherbrick"],
            ["Painting", "painting"],
            ["Paper", "paper"],
            ["Poisonous Potato", "poisonous_potato"],
            ["Porkchop", "porkchop"],
            ["Potato", "potato"],
            ["Potionitem", "potionitem"],
            ["Pumpkin Pie", "pumpkin_pie"],
            ["Pumpkin Seeds", "pumpkin_seeds"],
            ["Quartz", "quartz"],
            ["Record 11", "record_11"],
            ["Record 13", "record_13"],
            ["Record Blocks", "record_blocks"],
            ["Record Cat", "record_cat"],
            ["Record Chirp", "record_chirp"],
            ["Record Far", "record_far"],
            ["Record Mall", "record_mall"],
            ["Record Mellohi", "record_mellohi"],
            ["Record Stal", "record_stal"],
            ["Record Strad", "record_strad"],
            ["Record Wait", "record_wait"],
            ["Record Ward", "record_ward"],
            ["Redstone", "redstone"],
            ["Reeds", "reeds"],
            ["Repeater", "repeater"],
            ["Rotten Flesh", "rotten_flesh"],
            ["Saddle", "saddle"],
            ["Sign", "sign"],
            ["Skull", "skull"],
            ["Slime Ball", "slime_ball"],
            ["Snowball", "snowball"],
            ["Spawn Egg", "spawn_egg"],
            ["Speckled Melon", "speckled_melon"],
            ["Spider Eye", "spider_eye"],
            ["Stick", "stick"],
            ["Stone Axe", "stone_axe"],
            ["Stone Hoe", "stone_hoe"],
            ["Stone Pickaxe", "stone_pickaxe"],
            ["Stone Shovel", "stone_shovel"],
            ["Stone Sword", "stone_sword"],
            ["String", "string"],
            ["Sugar", "sugar"],
            ["Tnt Minecart", "tnt_minecart"],
            ["Water Bucket", "water_bucket"],
            ["Wheat", "wheat"],
            ["Wheat Seeds", "wheat_seeds"],
            ["Wooden Axe", "wooden_axe"],
            ["Wooden Door", "wooden_door"],
            ["Wooden Hoe", "wooden_hoe"],
            ["Wooden Pickaxe", "wooden_pickaxe"],
            ["Wooden Shovel", "wooden_shovel"],
            ["Wooden Sword", "wooden_sword"],
            ["Writable Book", "writable_book"],
            ["Written Book", "written_book"]
          
      ]
    }
  ],
  "output": "mciteminput",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mciteminput'] = function(block) {
  var dropdown_item = block.getFieldValue('ITEM');
  
  var code = 'Items.' + dropdown_item;
  return [code, Blockly.Java.ORDER_NONE];
};



Blockly.Blocks['mcblockinput'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcblockinput",
  "message0": "Block %1",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "BLOCK",
      "options": [
                ["Acacia Stairs", "acacia_stairs"],
                ["Activator Rail", "activator_rail"],
                ["Air", "air"],
                ["Anvil", "anvil"],
                ["Beacon", "beacon"],
                ["Bed", "bed"],
                ["Bedrock", "bedrock"],
                ["Birch Stairs", "birch_stairs"],
                ["Bookshelf", "bookshelf"],
                ["Brewing Stand", "brewing_stand"],
                ["Brick", "brick_"],
                ["Brick Stairs", "brick_stairs"],
                ["Brown Mushroom", "brown_mushroom"],
                ["Brown Mushroom", "brown_mushroom_"],
                ["Cactus", "cactus"],
                ["Cake", "cake"],
                ["Carpet", "carpet"],
                ["Carrots", "carrots"],
                ["Cauldron", "cauldron"],
                ["Chest", "chest"],
                ["Clay", "clay"],
                ["Coal", "coal_block"],
                ["Coal Ore", "coal_ore"],
                ["Cobblestone", "cobblestone"],
                ["Cobblestone Wall", "cobblestone_wall"],
                ["Cocoa", "cocoa"],
                ["Command", "command"],
                ["Crafting Table", "crafting_table"],
                ["Dark Oak Stairs", "dark_oak_stairs"],
                ["Daylight Detector", "daylight_detector"],
                ["Deadbush", "deadbush"],
                ["Detector Rail", "detector_rail"],
                ["Diamond", "diamond_block"],
                ["Diamond Ore", "diamond_ore"],
                ["Dirt", "dirt"],
                ["Dispenser", "dispenser"],
                ["Double Plant", "double_plant"],
                ["Double Stone Slab", "double_stone_slab"],
                ["Double Wooden Slab", "double_wooden_slab"],
                ["Dragon Egg", "dragon_egg"],
                ["Dropper", "dropper"],
                ["Emerald", "emerald_block"],
                ["Emerald Ore", "emerald_ore"],
                ["Enchanting Table", "enchanting_table"],
                ["End Portal", "end_portal"],
                ["End Portal Frame", "end_portal_frame"],
                ["End Stone", "end_stone"],
                ["Ender Chest", "ender_chest"],
                ["Farmland", "farmland"],
                ["Fence", "fence"],
                ["Fence Gate", "fence_gate"],
                ["Fire", "fire"],
                ["Flower Pot", "flower_pot"],
                ["Flowing Lava", "flowing_lava"],
                ["Flowing Water", "flowing_water"],
                ["Furnace", "furnace"],
                ["Glass", "glass"],
                ["Glass Pane", "glass_pane"],
                ["Glowstone", "glowstone"],
                ["Gold", "gold_block"],
                ["Gold Ore", "gold_ore"],
                ["Golden Rail", "golden_rail"],
                ["Grass", "grass"],
                ["Gravel", "gravel"],
                ["Hardened Clay", "hardened_clay"],
                ["Hay", "hay"],
                ["Heavy Weighted Pressure Plate", "heavy_weighted_pressure_plate"],
                ["Hopper", "hopper"],
                ["Ice", "ice"],
                ["Iron", "iron_block"],
                ["Iron Bars", "iron_bars"],
                ["Iron Door", "iron_door"],
                ["Iron Ore", "iron_ore"],
                ["Jukebox", "jukebox"],
                ["Jungle Stairs", "jungle_stairs"],
                ["Ladder", "ladder"],
                ["Lapis", "lapis_block"],
                ["Lapis Ore", "lapis_ore"],
                ["Lava", "lava"],
                ["Leaves", "leaves"],
                ["Leaves2", "leaves2"],
                ["Lever", "lever"],
                ["Light Weighted Pressure Plate", "light_weighted_pressure_plate"],
                ["Lit Furnace", "lit_furnace"],
                ["Lit Pumpkin", "lit_pumpkin"],
                ["Lit Redstone Lamp", "lit_redstone_lamp"],
                ["Lit Redstone Ore", "lit_redstone_ore"],
                ["Log", "log"],
                ["Log2", "log2"],
                ["Melon", "melon"],
                ["Melon Stem", "melon_stem"],
                ["Mob Spawner", "mob_spawner"],
                ["Monster Egg", "monster_egg"],
                ["Mossy Cobblestone", "mossy_cobblestone"],
                ["Mycelium", "mycelium"],
                ["Nether Brick", "nether_brick"],
                ["Nether Brick Fence", "nether_brick_fence"],
                ["Nether Brick Stairs", "nether_brick_stairs"],
                ["Nether Wart", "nether_wart"],
                ["Netherrack", "netherrack"],
                ["Note", "note"],
                ["Oak Stairs", "oak_stairs"],
                ["Obsidian", "obsidian"],
                ["Packed Ice", "packed_ice"],
                ["Piston", "piston"],
                ["Piston Extension", "piston_extension"],
                ["Piston Head", "piston_head"],
                ["Planks", "planks"],
                ["Portal", "portal"],
                ["Potatoes", "potatoes"],
                ["Powered Comparator", "powered_comparator"],
                ["Powered Repeater", "powered_repeater"],
                ["Pumpkin", "pumpkin"],
                ["Pumpkin Stem", "pumpkin_stem"],
                ["Quartz", "quartz_block"],
                ["Quartz Ore", "quartz_ore"],
                ["Quartz Stairs", "quartz_stairs"],
                ["Rail", "rail"],
                ["Red Flower", "red_flower"],
                ["Red Mushroom", "red_mushroom"],
                ["Redstone", "redstone_block"],
                ["Redstone Lamp", "redstone_lamp"],
                ["Redstone Ore", "redstone_ore"],
                ["Redstone Torch", "redstone_torch"],
                ["Redstone Wire", "redstone_wire"],
                ["Reeds", "reeds"],
                ["Sand", "sand"],
                ["Sandstone", "sandstone"],
                ["Sandstone Stairs", "sandstone_stairs"],
                ["Sapling", "sapling"],
                ["Skull", "skull"],
                ["Snow", "snow"],
                ["Snow Layer", "snow_layer"],
                ["Soul Sand", "soul_sand"],
                ["Sponge", "sponge"],
                ["Spruce Stairs", "spruce_stairs"],
                ["Stained Glass", "stained_glass"],
                ["Stained Glass Pane", "stained_glass_pane"],
                ["Stained Hardened Clay", "stained_hardened_clay"],
                ["Standing Sign", "standing_sign"],
                ["Sticky Piston", "sticky_piston"],
                ["Stone", "stone"],
                ["Stone Brick Stairs", "stone_brick_stairs"],
                ["Stone Button", "stone_button"],
                ["Stone Pressure Plate", "stone_pressure_plate"],
                ["Stone Slab", "stone_slab"],
                ["Stone Stairs", "stone_stairs"],
                ["Stonebrick", "stonebrick"],
                ["Tallgrass", "tallgrass"],
                ["Tnt", "tnt"],
                ["Torch", "torch"],
                ["Trapdoor", "trapdoor"],
                ["Trapped Chest", "trapped_chest"],
                ["Tripwire", "tripwire"],
                ["Tripwire Hook", "tripwire_hook"],
                ["Unlit Redstone Torch", "unlit_redstone_torch"],
                ["Unpowered Comparator", "unpowered_comparator"],
                ["Unpowered Repeater", "unpowered_repeater"],
                ["Vine", "vine"],
                ["Wall Sign", "wall_sign"],
                ["Water", "water"],
                ["Waterlily", "waterlily"],
                ["Web", "web"],
                ["Wheat", "wheat"],
                ["Wooden Button", "wooden_button"],
                ["Wooden Door", "wooden_door"],
                ["Wooden Pressure Plate", "wooden_pressure_plate"],
                ["Wooden Slab", "wooden_slab"],
                ["Wool", "wool"],
                ["Yellow Flower", "yellow_flower"],   
      ]
    }
  ],
  "output": "mcblockinput",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mcblockinput'] = function(block) {
  var dropdown_block = block.getFieldValue('BLOCK');
  
  var code = 'Blocks.' + dropdown_block;
  return [code, Blockly.Java.ORDER_NONE];
};



Blockly.Blocks['mcaction_spawnitem'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_spawnitem",
  "message0": "Spawn Item %1 Item %2 Location X %3 Location Y %4 Location Z %5",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "ITEM",
      "check": "mciteminput"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_spawnitem'] = function(block) {
  var value_item = Blockly.Java.valueToCode(block, 'ITEM', Blockly.Java.ORDER_ATOMIC);
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);



  var code = 
  'if(!world.isRemote){\n' +
  '    world.spawnEntityInWorld(new EntityItem(world, '+ value_loc_x + ' + 0.5f, ' + value_loc_y + ' + 1, ' + value_loc_z + ' + 0.5f, new ItemStack' + value_item + '));\n' +
  '}\n';
  return code;
};



Blockly.Blocks['mcitem'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcitem",
  "message0": "Minecraft Item %1 Name:  %2 %3 Max Stack Size: %4 %5 Options: %6 %7",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_input",
      "name": "NAME",
      "text": "change_me"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_input",
      "name": "AMOUNT",
      "text": "64"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE",
      "check": "optionitem"
    }
  ],
  "inputsInline": false,
  "colour": 15,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mcitem'] = function(block) {
  var value_name = make_java_id(block.getFieldValue('NAME'));
  var raw_value_name = block.getFieldValue('NAME');
  var value_amount = block.getFieldValue('AMOUNT');
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');

  if(isNaN(parseInt(value_amount))){
    showError(block, "Max Stack Size must be a number!");
  }



  var code = 
  '    public class Mcitem_' + value_name + ' extends ItemBase {\n' + 
  '        public Mcitem_' + value_name + '() {\n' +
  '            super(BLOCK_ID, CREATIVE_TAB, "' + raw_value_name + '", ' + value_amount + '); \n' +
  '        }\n\n' +
          statements_code +
    '    }\n';
  return code;
};





Blockly.Blocks['mcitemoptions_rightclick'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcitemoptions_rightclick",
  "message0": "On Right Click %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE",
      "check": "action"
    }
  ],
  "previousStatement": "optionitem",
  "nextStatement": "optionitem",
  "colour": 20,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcitemoptions_rightclick'] = function(block) {
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');
  
  statements_code = statements_code.replace(RETURNS, 'itemstack');

  var code = 
  'public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){\n' +
  '    EntityLiving entity = null;\n' +
  '    ' + statements_code + '\n' +
  '    return itemstack;\n' +
  '}'
  ;

  return code;
};



Blockly.Blocks['mcitemoptions_leftclick'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcitemoptions_leftclick",
  "message0": "On Left Click %1 %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "CODE",
      "check": "action"
    }
  ],
  "previousStatement": "optionitem",
  "nextStatement": "optionitem",
  "colour": 20,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcitemoptions_leftclick'] = function(block) {
  var statements_code = Blockly.Java.statementToCode(block, 'CODE');
  
  statements_code = statements_code.replace(RETURNS, 'true');

  var code = 
  'public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int meta, float dx, float dy, float dz) {\n' +
  '    EntityLiving entity = null;\n' +
  '    ' + statements_code + '\n' +
  '    return true;\n' +
  '}'
  ;

  return code;
};


Blockly.Blocks['mcitemoptions_lore'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcitemoptions_lore",
  "message0": "(NI) Lore %1",
  "args0": [
    {
      "type": "input_value",
      "name": "LORE",
      "check": [
        "Array",
        "String"
      ]
    }
  ],
  "previousStatement": "optionitem",
  "nextStatement": "optionitem",
  "colour": 20,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcitemoptions_lore'] = function(block) {
    var value_name = Blockly.Java.valueToCode(block, 'LORE', Blockly.Java.ORDER_ATOMIC);
    var code = '/*Lore Code*/\n';
    return code;
}


Blockly.Blocks['location_player_x'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_player_x",
  "message0": "Player X",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_player_x'] = function(block) {
  return ['player.posX', Blockly.JavaScript.ORDER_NONE];
};



Blockly.Blocks['location_player_y'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_player_y",
  "message0": "Player Y",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_player_y'] = function(block) {
  return ['player.posY', Blockly.JavaScript.ORDER_NONE];
};



Blockly.Blocks['location_player_z'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_player_z",
  "message0": "Player Z",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_player_z'] = function(block) {
  return ['player.posZ', Blockly.JavaScript.ORDER_NONE];
};



Blockly.Blocks['location_entity_x'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_entity_x",
  "message0": "Entity X",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_entity_x'] = function(block) {
  return ['entity.posX', Blockly.JavaScript.ORDER_NONE];
};



Blockly.Blocks['location_entity_y'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_entity_y",
  "message0": "Entity Y",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_entity_y'] = function(block) {
  return ['entity.posY', Blockly.JavaScript.ORDER_NONE];
};



Blockly.Blocks['location_entity_z'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_entity_z",
  "message0": "Entity Z",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_entity_z'] = function(block) {
  return ['entity.posZ', Blockly.JavaScript.ORDER_NONE];
};



Blockly.Blocks['location_block_x'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_block_x",
  "message0": "Block X",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_block_x'] = function(block) {
  return ['x', Blockly.JavaScript.ORDER_NONE];
};





Blockly.Blocks['location_block_y'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_block_y",
  "message0": "Block Y",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_block_y'] = function(block) {
  return ['y', Blockly.JavaScript.ORDER_NONE];
};




Blockly.Blocks['location_block_z'] = {
  
  init: function() {
    this.jsonInit({
      "type": "location_block_z",
  "message0": "Block Z",
  "output": "Number",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['location_block_z'] = function(block) {
  return ['z', Blockly.JavaScript.ORDER_NONE];
};



Blockly.Blocks['mcaction_giveitem'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_giveitem",
  "message0": "Give Player Item %1",
  "args0": [
    {
      "type": "input_value",
      "name": "ITEM",
      "check": "mciteminput"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_giveitem'] = function(block) {
  var value_item = Blockly.Java.valueToCode(block, 'ITEM', Blockly.Java.ORDER_ATOMIC);
  
  var code = 'if(!world.isRemote){{if(player != null){player.inventory.addItemStackToInventory(new ItemStack' + value_item + ');}}\n';
  return code;
};



Blockly.Blocks['mcaction_lightning'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_lightning",
  "message0": "Strike Lightning %1 Location X: %2 Location Y: %3 Location Z: %4",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_lightning'] = function(block) {
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);
 
  var code = 'if(!world.isRemote){world.addWeatherEffect((new EntityLightningBolt(world, ' + value_loc_x + ', ' + value_loc_y + ', ' + value_loc_z + ')));}\n';
  return code;
};




Blockly.Blocks['mcaction_log'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_log",
  "message0": "Console Log %1 Message:  %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "MSG",
      "check": "String"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_log'] = function(block) {
  var value_msg = Blockly.Java.valueToCode(block, 'MSG', Blockly.Java.ORDER_ATOMIC);
  
  var code = 
  'if(!world.isRemote){\n' +
  '    PLog.game(' + value_msg + ');\n' +
  '}\n';
  return code;
};


Blockly.Blocks['mcaction_chat'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_log",
  "message0": "Chat Message %1 Message:  %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "MSG",
      "check": "String"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_chat'] = function(block) {
  var value_msg = Blockly.Java.valueToCode(block, 'MSG', Blockly.Java.ORDER_ATOMIC);
  
  var code = 
  'if(world.isRemote){\n' +
  '    if(player != null) {player.addChatMessage(new ChatComponentText(' + value_msg + '));}\n' + 
  '}\n';
  return code;
};






Blockly.Blocks['mcaction_placeblock'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_placeblock",
  "message0": "Place Block %1 Block %2 Location X %3 Location Y %4 Location Z %5",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "BLOCK",
      "check": "mcblockinput"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mcaction_placeblock'] = function(block) {
  var value_block = Blockly.Java.valueToCode(block, 'BLOCK', Blockly.Java.ORDER_ATOMIC);
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);

  var code = 'if(!world.isRemote){world.setBlock((int)' + value_loc_x + ', (int)' + value_loc_y + ', (int)' + value_loc_z + ', ' + value_block + ', 0, 3);}\n';
  return code;
};






Blockly.Blocks['mcaction_placeblockmeta'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_placeblockmeta",
  "message0": "Place Block %1 Block %2 Block Meta %3 Location X %4 Location Y %5 Location Z %6",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "BLOCK",
      "check": "mcblockinput"
    },
    {
      "type": "input_value",
      "name": "META",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_placeblockmeta'] = function(block) {
  var value_block = Blockly.Java.valueToCode(block, 'BLOCK', Blockly.Java.ORDER_ATOMIC);
  var value_meta = Blockly.Java.valueToCode(block, 'META', Blockly.Java.ORDER_ATOMIC);
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);

  var code = 'if(!world.isRemote){world.setBlock((int)' + value_loc_x + ', (int)' + value_loc_y + ', (int)' + value_loc_z + ', ' + value_block + ', (int)' + value_meta +', 3);}\n';
  return code;
};






Blockly.Blocks['mcaction_breakblock'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_breakblock",
  "message0": "Break Block %1 Drop Block %2 %3 Location X %4 Location Y %5 Location Z %6",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "DROP",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_breakblock'] = function(block) {
  var checkbox_drop = block.getFieldValue('DROP') == 'TRUE';
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);
  
  var code = 'if(!world.isRemote){world.func_147480_a((int)' + value_loc_x + ', (int)' + value_loc_y + ', (int)' + value_loc_z +', ' + checkbox_drop + ');}\n';
  return code;
};




Blockly.Blocks['mcaction_velocity_player'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_velocity_player",
  "message0": "Set Player Velocity %1 Velocity X %2 Velocity Y %3 Velocity Z %4",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "VEL_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "VEL_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "VEL_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mcaction_velocity_player'] = function(block) {
  var value_vel_x = Blockly.Java.valueToCode(block, 'VEL_X', Blockly.Java.ORDER_ATOMIC);
  var value_vel_y = Blockly.Java.valueToCode(block, 'VEL_Y', Blockly.Java.ORDER_ATOMIC);
  var value_vel_z = Blockly.Java.valueToCode(block, 'VEL_Z', Blockly.Java.ORDER_ATOMIC);

  var code = 'if(player != null){player.setVelocity(' + value_vel_x + ', ' + value_vel_y + ', ' + value_vel_z +');}\n';
  return code;
};


Blockly.Blocks['mcaction_velocity_entity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_velocity_entity",
  "message0": "Set Entity Velocity %1 Velocity X %2 Velocity Y %3 Velocity Z %4",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "VEL_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "VEL_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "VEL_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_velocity_entity'] = function(block) {
  var value_vel_x = Blockly.Java.valueToCode(block, 'VEL_X', Blockly.Java.ORDER_ATOMIC);
  var value_vel_y = Blockly.Java.valueToCode(block, 'VEL_Y', Blockly.Java.ORDER_ATOMIC);
  var value_vel_z = Blockly.Java.valueToCode(block, 'VEL_Z', Blockly.Java.ORDER_ATOMIC);

  var code = 'if(entity != null){entity.setVelocity(' + value_vel_x + ', ' + value_vel_y + ', ' + value_vel_z +');}\n';
  return code;
};



Blockly.Blocks['mcaction_teleport_player'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_teleport_player",
  "message0": "Teleport Player %1 Location X %2 Location Y %3 Location Z %4",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mcaction_teleport_player'] = function(block) {
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);
  
  var code = 'if(player != null){player.setPositionAndUpdate(' + value_loc_x + ', ' + value_loc_y + ', ' + value_loc_z +');}\n';
  return code;
};




Blockly.Blocks['mcaction_teleport_entity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_teleport_entity",
  "message0": "Teleport Entity %1 Location X %2 Location Y %3 Location Z %4",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mcaction_teleport_entity'] = function(block) {
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);
  
  var code = 'if(entity != null){((EntityLiving)entity).setPositionAndUpdate(' + value_loc_x + ', ' + value_loc_y + ', ' + value_loc_z +');}\n';
  return code;
};




Blockly.Blocks['mcaction_firework'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_firework",
  "message0": "Launch Firework %1 Location X %2 Location Y %3 Location Z %4 Power %5 Flicker %6 %7 Trail %8 %9 Type %10 %11 Color %12",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "LOC_X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "LOC_Z",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "POWER",
      "check": "Number"
    },
    {
      "type": "field_checkbox",
      "name": "FLICKER",
      "checked": true
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "TRAIL",
      "checked": true
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_dropdown",
      "name": "TYPE",
      "options": [
        [
          "Small Ball",
          "0"
        ],
        [
          "Large Ball",
          "1"
        ],
        [
          "Star",
          "2"
        ],
        [
          "Creeper",
          "3"
        ],
        [
          "Burst",
          "4"
        ]
      ]
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "COLOR",
      "check": [
        "String",
        "Array"
      ]
    }
  ],
  "inputsInline": false,
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_firework'] = function(block) {
  var value_loc_x = Blockly.Java.valueToCode(block, 'LOC_X', Blockly.Java.ORDER_ATOMIC);
  var value_loc_y = Blockly.Java.valueToCode(block, 'LOC_Y', Blockly.Java.ORDER_ATOMIC);
  var value_loc_z = Blockly.Java.valueToCode(block, 'LOC_Z', Blockly.Java.ORDER_ATOMIC);
  var value_power = Blockly.Java.valueToCode(block, 'POWER', Blockly.Java.ORDER_ATOMIC);
  var checkbox_flicker = block.getFieldValue('FLICKER') == 'TRUE';
  var checkbox_trail = block.getFieldValue('TRAIL') == 'TRUE';
  var dropdown_type = block.getFieldValue('TYPE');
  var value_color = Blockly.Java.valueToCode(block, 'COLOR', Blockly.Java.ORDER_ATOMIC);
  
  var code = 'if(!world.isRemote){world.spawnEntityInWorld(ModHelpers.getFirework(world, ' + value_loc_x + ', ' + value_loc_y + ', ' + value_loc_z + ', ' + checkbox_flicker + ', ' + checkbox_trail + ', ' + value_color + ', ' + dropdown_type + ', ' + value_power + '));}\n';
  return code;
};





Blockly.Blocks['mcvariable_color_chat'] = {
  init: function() {

    var colour = new Blockly.FieldColour('#AA0000');
    colour.setColours(['#000', '#0000AA', '#00AA00', '#00AAAA', '#AA0000', '#AA00AA', '#FFAA00', '#AAAAAA', '#555555', '#5555FF', '#55FF55', '#55FFFF', '#FF5555', '#FF55FF', '#FFFF55', '#FFFFFF']).setColumns(4);

   this.setOutput(true, 'String');
   this.setColour(290);

    this.appendDummyInput()
        .appendField('Chat Color:')
        .appendField(colour, 'COLOR');

  }
};

Blockly.Java['mcvariable_color_chat'] = function(block) {
  var colour_color = block.getFieldValue('COLOR');
  var code = '"' + colour_color + '"';
  return [code, Blockly.Java.ORDER_NONE];
};




Blockly.Blocks['mcvariable_color_fireworks'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcvariable_color_fireworks",
  "message0": "Firework Color: %1",
  "args0": [
    {
      "type": "field_colour",
      "name": "COLOR",
      "colour": "#ff0000"
    }
  ],
  "output": "String",
  "colour": 290,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};


Blockly.Java['mcvariable_color_fireworks'] = function(block) {
  var colour_color = block.getFieldValue('COLOR');
  var code = '"' + colour_color + '"';
  return [code, Blockly.Java.ORDER_NONE];
};





Blockly.Blocks['mcaction_rename_entity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_rename_entity",
  "message0": "Rename Mob %1 Name: %2",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "NAME",
      "check": "String"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_rename_entity'] = function(block) {
  var value_name = Blockly.Java.valueToCode(block, 'NAME', Blockly.Java.ORDER_ATOMIC);
  
  var code = 
  'if(entity != null){\n' +
  '    entity.setCustomNameTag(' + value_name + ');\n' +
  '    entity.setAlwaysRenderNameTag(true);\n' +
  '}\n'
  ;
  return code;
};




Blockly.Blocks['mcaction_sethealth_entity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_sethealth_entity",
  "message0": "Set Mob Health %1",
  "args0": [
    {
      "type": "input_value",
      "name": "HEALTH",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_sethealth_entity'] = function(block) {
  var value_health = Blockly.Java.valueToCode(block, 'HEALTH', Blockly.Java.ORDER_ATOMIC);
  
  var code = 'entity.setHealth(' + value_health + ');\n';
  return code;
};



Blockly.Blocks['mcaction_sethealth_player'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcaction_sethealth_player",
  "message0": "Set Player Health %1",
  "args0": [
    {
      "type": "input_value",
      "name": "HEALTH",
      "check": "Number"
    }
  ],
  "previousStatement": "action",
  "nextStatement": "action",
  "colour": 140,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcaction_sethealth_entity'] = function(block) {
  var value_health = Blockly.Java.valueToCode(block, 'HEALTH', Blockly.Java.ORDER_ATOMIC);
  
  var code = 'player.setHealth(' + value_health + ');\n';
  return code;
};




Blockly.Blocks['mcentity'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcentity",
  "message0": "Minecraft Mob %1 Name: %2 %3 Model: %4 %5 Create Spawn Egg %6 %7 Spawn Egg Primary Color %8 %9 Spawn Egg Secondary Color %10 %11 (NI) Spawn Naturally In World %12 %13 Options: %14 %15",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "field_input",
      "name": "NAME",
      "text": "Mob Name"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_dropdown",
      "name": "MODEL",
      "options": [
        [
          "Bat",
          "BatNew"
        ],
        [
          "Player",
          "Biped"
        ],
        [
          "Blaze",
          "Blaze"
        ],
        [
          "Boat",
          "Boat"
        ],
        [
          "Book",
          "Book"
        ],
        [
          "Chicken",
          "Chicken"
        ],
        [
          "Cow",
          "Cow"
        ],
        [
          "Creeper",
          "Creeper"
        ],
        /*[
          "Dragon",
          "Dragon"
        ],*/
        [
          "Enderman",
          "Enderman"
        ],
        [
          "Ghast",
          "Ghast"
        ],
        /*[
          "Horse",
          "Horse"
        ],*/
        [
          "Iron Golem",
          "IronGolem"
        ],
        [
          "Leash Knot",
          "LeashKnot"
        ],
        /*[
          "Magma Cube",
          "MagmaCube"
        ],*/
        [
          "Minecart",
          "Minecart"
        ],
        [
          "Ocelot",
          "OcelotNew"
        ],
        [
          "Pig",
          "Pig"
        ],
        /*[
          "Sheep1",
          "Sheep1"
        ],
        [
          "Sheep2",
          "Sheep2"
        ],*/
        [
          "Silverfish",
          "Silverfish"
        ],
        [
          "Skeleton",
          "SkeletonNew"
        ],
        [
          "Skeleton Head",
          "SkeletonHead"
        ],
        [
          "Slime",
          "Slime"
        ],
        [
          "Snowman",
          "SnowMan"
        ],
        [
          "Spider",
          "Spider"
        ],
        [
          "Squid",
          "Squid"
        ],
        [
          "Villager",
          "Villager"
        ],
        [
          "Witch",
          "Witch"
        ],
        /*[
          "Wither",
          "Wither"
        ],*/
        [
          "Wolf",
          "WolfNew"
        ],
        [
          "Zombie",
          "Zombie"
        ],
        [
          "Zombie Villager",
          "ZombieVillager"
        ]
        
      ]
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "MAKE_EGG",
      "checked": true
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_colour",
      "name": "EGG_P",
      "colour": "#ff0000"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_colour",
      "name": "EGG_S",
      "colour": "#33ff33"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_checkbox",
      "name": "SPAWN_NATURALLY",
      "checked": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "input_statement",
      "name": "OPTIONS",
      "check": "mcentityoptions"
    }
  ],
  "colour": 230,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};



Blockly.Java['mcentity'] = function(block) {
  var value_name = make_java_id(block.getFieldValue('NAME'));
  var raw_value_name = block.getFieldValue('NAME');
  var dropdown_model = block.getFieldValue('MODEL');
  var checkbox_make_egg = block.getFieldValue('MAKE_EGG') == 'TRUE';
  var colour_egg_p = block.getFieldValue('EGG_P');
  var colour_egg_s = block.getFieldValue('EGG_S');
  var checkbox_spawn_naturally = block.getFieldValue('SPAWN_NATURALLY') == 'TRUE';
  var statements_options = Blockly.Java.statementToCode(block, 'OPTIONS');

  colour_egg_p = colour_egg_p.replace('#', '0x');
  colour_egg_s = colour_egg_s.replace('#', '0x');

  if(!checkbox_make_egg){
    colour_egg_p = -1;
    colour_egg_s = -1;
  }

  var code = 
  'public static class Mcentity_' + value_name + ' extends EntityCreature {\n' +
  '    public static final String RAW_NAME = "' + raw_value_name + '";\n' +
  '    public static final String NAME = "' + value_name + '";\n' +
  '    public static final String MODEL = "' + dropdown_model + '";\n' +
  '    public static final boolean SPAWN_NATURALLY = ' + checkbox_spawn_naturally + ';\n' +
  '    public static final int EGG_P = ' + colour_egg_p + ';\n' +
  '    public static final int EGG_S = ' + colour_egg_s + ';\n' +
  '\n' +
  '    public Mcentity_' + value_name + '(World world){\n' +
  '        super(world);\n' +
  '    }\n' +
  '\n' +
  '        ' + statements_options + '\n' +
     

  '}\n'

  ;
  return code;
};

Blockly.Blocks['mcentityoptions_modelscale'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcentityoptions_modelscale",
  "message0": "Scale Model %1 Scale X: %2 Scale Y: %3 Scale Z: %4",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "Z",
      "check": "Number"
    }
  ],
  "previousStatement": "mcentityoptions",
  "nextStatement": "mcentityoptions",
  "colour": 230,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcentityoptions_modelscale'] = function(block) {
  var value_x = Blockly.Java.valueToCode(block, 'X', Blockly.Java.ORDER_ATOMIC);
  var value_y = Blockly.Java.valueToCode(block, 'Y', Blockly.Java.ORDER_ATOMIC);
  var value_z = Blockly.Java.valueToCode(block, 'Z', Blockly.Java.ORDER_ATOMIC);
  
  var code = '//Scale ' + value_x + ',' + value_y + ',' + value_z + ';\n';
  return code;
};

Blockly.Blocks['mcentityoptions_modeltranslate'] = {
  
  init: function() {
    this.jsonInit({
      "type": "mcentityoptions_modeltranslate",
  "message0": "Translate Model %1 Move X: %2 Move Y: %3 Move Z: %4",
  "args0": [
    {
      "type": "input_dummy"
    },
    {
      "type": "input_value",
      "name": "X",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "Y",
      "check": "Number"
    },
    {
      "type": "input_value",
      "name": "Z",
      "check": "Number"
    }
  ],
  "previousStatement": "mcentityoptions",
  "nextStatement": "mcentityoptions",
  "colour": 230,
  "tooltip": "",
  "helpUrl": ""
    });
  }
};

Blockly.Java['mcentityoptions_modeltranslate'] = function(block) {
  var value_x = Blockly.Java.valueToCode(block, 'X', Blockly.Java.ORDER_ATOMIC);
  var value_y = Blockly.Java.valueToCode(block, 'Y', Blockly.Java.ORDER_ATOMIC);
  var value_z = Blockly.Java.valueToCode(block, 'Z', Blockly.Java.ORDER_ATOMIC);
  
  var code = '//Translate ' + value_x + ',' + value_y + ',' + value_z + ';\n';
  return code;
};