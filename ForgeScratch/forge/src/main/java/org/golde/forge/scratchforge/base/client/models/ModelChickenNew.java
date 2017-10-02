package org.golde.forge.scratchforge.base.client.models;


import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelChickenNew extends ModelBase
{
  public ModelRenderer head;
  public ModelRenderer body;
  public ModelRenderer rightLeg;
  public ModelRenderer leftLeg;
  public ModelRenderer rightWing;
  public ModelRenderer leftWing;
  public ModelRenderer bill;
  public ModelRenderer chin;
  
  public ModelChickenNew()
  {
    int i = 16;
    this.head = new ModelRenderer(this, 0, 0);
    this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
    this.head.setRotationPoint(0.0F, -1 + i, -4.0F);
    
    this.bill = new ModelRenderer(this, 14, 0);
    this.bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
    this.bill.setRotationPoint(0.0F, -1 + i, -4.0F);
    
    this.chin = new ModelRenderer(this, 14, 4);
    this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
    this.chin.setRotationPoint(0.0F, -1 + i, -4.0F);
    
    this.body = new ModelRenderer(this, 0, 9);
    this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
    this.body.setRotationPoint(0.0F, i, 0.0F);
    
    this.rightLeg = new ModelRenderer(this, 26, 0);
    this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
    this.rightLeg.setRotationPoint(-2.0F, 3 + i, 1.0F);
    
    this.leftLeg = new ModelRenderer(this, 26, 0);
    this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
    this.leftLeg.setRotationPoint(1.0F, 3 + i, 1.0F);
    
    this.rightWing = new ModelRenderer(this, 24, 13);
    this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
    this.rightWing.setRotationPoint(-4.0F, -3 + i, 0.0F);
    
    this.leftWing = new ModelRenderer(this, 24, 13);
    this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
    this.leftWing.setRotationPoint(4.0F, -3 + i, 0.0F);
  }
  
  public void render(Entity p_render_1_, float p_render_2_, float p_render_3_, float p_render_4_, float p_render_5_, float p_render_6_, float p_render_7_)
  {
    setRotationAngles(p_render_2_, p_render_3_, p_render_4_, p_render_5_, p_render_6_, p_render_7_, p_render_1_);
    if (this.isChild)
    {
      float f = 2.0F;
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 5.0F * p_render_7_, 2.0F * p_render_7_);
      this.head.render(p_render_7_);
      this.bill.render(p_render_7_);
      this.chin.render(p_render_7_);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.0F / f, 1.0F / f, 1.0F / f);
      GL11.glTranslatef(0.0F, 24.0F * p_render_7_, 0.0F);
      this.body.render(p_render_7_);
      this.rightLeg.render(p_render_7_);
      this.leftLeg.render(p_render_7_);
      this.rightWing.render(p_render_7_);
      this.leftWing.render(p_render_7_);
      GL11.glPopMatrix();
    }
    else
    {
      this.head.render(p_render_7_);
      this.bill.render(p_render_7_);
      this.chin.render(p_render_7_);
      this.body.render(p_render_7_);
      this.rightLeg.render(p_render_7_);
      this.leftLeg.render(p_render_7_);
      this.rightWing.render(p_render_7_);
      this.leftWing.render(p_render_7_);
    }
  }
  
  public void setRotationAngles(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_)
  {
    this.head.rotateAngleX = (p_setRotationAngles_5_ / 57.295776F);
    this.head.rotateAngleY = (p_setRotationAngles_4_ / 57.295776F);
    
    this.bill.rotateAngleX = this.head.rotateAngleX;
    this.bill.rotateAngleY = this.head.rotateAngleY;
    
    this.chin.rotateAngleX = this.head.rotateAngleX;
    this.chin.rotateAngleY = this.head.rotateAngleY;
    
    this.body.rotateAngleX = 1.5707964F;
    
    this.rightLeg.rotateAngleX = 0;
    this.leftLeg.rotateAngleX = 0;
    this.rightWing.rotateAngleZ = 0;
    this.leftWing.rotateAngleZ = 0;
  }
}

