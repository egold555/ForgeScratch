package org.golde.forge.scratchforge.base.client.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

@SideOnly(Side.CLIENT)
public class ModelIronGolemNew extends ModelBase
{
	public ModelRenderer ironGolemHead;
	public ModelRenderer ironGolemBody;
	public ModelRenderer ironGolemRightArm;
	public ModelRenderer ironGolemLeftArm;
	public ModelRenderer ironGolemLeftLeg;
	public ModelRenderer ironGolemRightLeg;

	public ModelIronGolemNew()
	{
		this(0.0F);
	}

	public ModelIronGolemNew(float p_i1161_1_)
	{
		this(p_i1161_1_, -7.0F);
	}

	public ModelIronGolemNew(float p_i1162_1_, float p_i1162_2_)
	{
		int i = 128;
		int j = 128;

		this.ironGolemHead = new ModelRenderer(this).setTextureSize(i, j);
		this.ironGolemHead.setRotationPoint(0.0F, 0.0F + p_i1162_2_, -2.0F);
		this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, p_i1162_1_);
		this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, p_i1162_1_);

		this.ironGolemBody = new ModelRenderer(this).setTextureSize(i, j);
		this.ironGolemBody.setRotationPoint(0.0F, 0.0F + p_i1162_2_, 0.0F);
		this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, p_i1162_1_);
		this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, p_i1162_1_ + 0.5F);

		this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(i, j);
		this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, p_i1162_1_);

		this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(i, j);
		this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, p_i1162_1_);

		this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(i, j);
		this.ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + p_i1162_2_, 0.0F);
		this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i1162_1_);

		this.ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(i, j);
		this.ironGolemRightLeg.mirror = true;
		this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + p_i1162_2_, 0.0F);
		this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i1162_1_);
	}

	public void render(Entity p_render_1_, float p_render_2_, float p_render_3_, float p_render_4_, float p_render_5_, float p_render_6_, float p_render_7_)
	{
		setRotationAngles(p_render_2_, p_render_3_, p_render_4_, p_render_5_, p_render_6_, p_render_7_, p_render_1_);

		this.ironGolemHead.render(p_render_7_);
		this.ironGolemBody.render(p_render_7_);
		this.ironGolemLeftLeg.render(p_render_7_);
		this.ironGolemRightLeg.render(p_render_7_);
		this.ironGolemRightArm.render(p_render_7_);
		this.ironGolemLeftArm.render(p_render_7_);
	}

	public void setRotationAngles(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_)
	{
		this.ironGolemHead.rotateAngleY = (p_setRotationAngles_4_ / 57.295776F);
		this.ironGolemHead.rotateAngleX = (p_setRotationAngles_5_ / 57.295776F);

		this.ironGolemLeftLeg.rotateAngleX = (-1.5F * func_78172_a(p_setRotationAngles_1_, 13.0F) * p_setRotationAngles_2_);
		this.ironGolemRightLeg.rotateAngleX = (1.5F * func_78172_a(p_setRotationAngles_1_, 13.0F) * p_setRotationAngles_2_);
		this.ironGolemLeftLeg.rotateAngleY = 0.0F;
		this.ironGolemRightLeg.rotateAngleY = 0.0F;
	}

	public void setLivingAnimations(EntityLivingBase p_setLivingAnimations_1_, float p_setLivingAnimations_2_, float p_setLivingAnimations_3_, float p_setLivingAnimations_4_)
	{
		this.ironGolemRightArm.rotateAngleX = ((-0.2F + 1.5F * func_78172_a(p_setLivingAnimations_2_, 13.0F)) * p_setLivingAnimations_3_);
		this.ironGolemLeftArm.rotateAngleX = ((-0.2F - 1.5F * func_78172_a(p_setLivingAnimations_2_, 13.0F)) * p_setLivingAnimations_3_);
	}

	private float func_78172_a(float p_78172_1_, float p_78172_2_)
	{
		return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
	}
}
