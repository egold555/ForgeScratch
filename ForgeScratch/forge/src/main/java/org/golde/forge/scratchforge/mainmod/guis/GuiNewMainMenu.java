package org.golde.forge.scratchforge.mainmod.guis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.golde.forge.scratchforge.mainmod.Language;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Runnables;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiNewMainMenu extends GuiScreen
{
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Random RANDOM = new Random();
	private final float minceraftRoll;
	private String splashText;
	private GuiButton buttonResetDemo;
	private float panoramaTimer;
	private DynamicTexture viewportTexture;
	private final Object threadLock = new Object();
	public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
	private int openGLWarning2Width;
	private int openGLWarning1Width;
	private int openGLWarningX1;
	private int openGLWarningY1;
	private int openGLWarningX2;
	private int openGLWarningY2;
	private String openGLWarning1;
	private String openGLWarning2;
	private String openGLWarningLink;
	private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
	private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
	private static final ResourceLocation[] TITLE_PANORAMA_PATHS = { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
	private ResourceLocation backgroundTexture;
	private GuiButton realmsButton;
	private boolean hasCheckedForRealmsNotification;
	private GuiScreen realmsNotification;
	private int widthCopyright;
	private int widthCopyrightRest;
	private GuiButton modButton;
	//private NotificationModUpdateScreen modUpdateNotification;

	public GuiNewMainMenu()
	{
		this.openGLWarning2 = MORE_INFO_TEXT;
		this.splashText = "missingno";
		IResource iresource = null;
		try
		{
			List<String> list = Lists.newArrayList();
			iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
			String s;
			while ((s = bufferedreader.readLine()) != null)
			{
				s = s.trim();
				if (!s.isEmpty()) {
					list.add(s);
				}
			}
			if (!list.isEmpty()) {
				for (;;)
				{
					this.splashText = ((String)list.get(RANDOM.nextInt(list.size())));
					if (this.splashText.hashCode() != 125780783) {
						break;
					}
				}
			}
		}
		catch (IOException localIOException) {}finally
		{
			IOUtils.closeQuietly(iresource);
		}
		this.minceraftRoll = RANDOM.nextFloat();
		this.openGLWarning1 = "";
		if ((!GLContext.getCapabilities().OpenGL20) && (!OpenGlHelper.areShadersSupported()))
		{
			this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
			this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
			this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
		}
	}

	private boolean areRealmsNotificationsEnabled()
	{
		return (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS)) && (this.realmsNotification != null);
	}

	public void updateScreen()
	{
		if (areRealmsNotificationsEnabled()) {
			this.realmsNotification.updateScreen();
		}
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}

	protected void keyTyped(char p_keyTyped_1_, int p_keyTyped_2_)
			throws IOException
	{}

	public void initGui()
	{
		this.viewportTexture = new DynamicTexture(256, 256);
		this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
		this.widthCopyright = this.fontRenderer.getStringWidth("Copyright Mojang AB. Do not distribute!");
		this.widthCopyrightRest = (this.width - this.widthCopyright - 2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if ((calendar.get(2) + 1 == 12) && (calendar.get(5) == 24)) {
			this.splashText = "Merry X-mas!";
		} else if ((calendar.get(2) + 1 == 1) && (calendar.get(5) == 1)) {
			this.splashText = "Happy new year!";
		} else if ((calendar.get(2) + 1 == 10) && (calendar.get(5) == 31)) {
			this.splashText = "OOoooOOOoooo! Spooky!";
		}
		int i = 24;
		int j = this.height / 4 + 48;
		if (this.mc.isDemo()) {
			addDemoButtons(j, 24);
		} else {
			addSingleplayerMultiplayerButtons(j, 24);
		}
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
		this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
		this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, j + 72 + 12));
		synchronized (this.threadLock)
		{
			this.openGLWarning1Width = this.fontRenderer.getStringWidth(this.openGLWarning1);
			this.openGLWarning2Width = this.fontRenderer.getStringWidth(this.openGLWarning2);
			int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
			this.openGLWarningX1 = ((this.width - k) / 2);
			this.openGLWarningY1 = (((GuiButton)this.buttonList.get(0)).y - 24);
			this.openGLWarningX2 = (this.openGLWarningX1 + k);
			this.openGLWarningY2 = (this.openGLWarningY1 + 24);
		}
		this.mc.setConnectedToRealms(false);
		if ((Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS)) && (!this.hasCheckedForRealmsNotification))
		{
			RealmsBridge realmsbridge = new RealmsBridge();
			this.realmsNotification = realmsbridge.getNotificationScreen(this);
			this.hasCheckedForRealmsNotification = true;
		}
		if (areRealmsNotificationsEnabled())
		{
			this.realmsNotification.setGuiSize(this.width, this.height);
			this.realmsNotification.initGui();
		}
		
		//TODO: this.modUpdateNotification = NotificationModUpdateScreen.init(getMainMenuFromThis(this), this.modButton);
		
	}

	private void addSingleplayerMultiplayerButtons(int p_addSingleplayerMultiplayerButtons_1_, int p_addSingleplayerMultiplayerButtons_2_)
	{
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_addSingleplayerMultiplayerButtons_1_, I18n.format("menu.singleplayer", new Object[0])));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_addSingleplayerMultiplayerButtons_1_ + p_addSingleplayerMultiplayerButtons_2_ * 1, I18n.format("menu.multiplayer", new Object[0])));
		this.realmsButton = addButton(new GuiButton(14, this.width / 2 + 2, p_addSingleplayerMultiplayerButtons_1_ + p_addSingleplayerMultiplayerButtons_2_ * 2, 98, 20, I18n.format("menu.online", new Object[0]).replace("Minecraft", "").trim()));
		this.buttonList.add(this.modButton = new GuiButton(6, this.width / 2 - 100, p_addSingleplayerMultiplayerButtons_1_ + p_addSingleplayerMultiplayerButtons_2_ * 2, 98, 20, I18n.format("fml.menu.mods", new Object[0])));
	}

	private void addDemoButtons(int p_addDemoButtons_1_, int p_addDemoButtons_2_)
	{
		this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_addDemoButtons_1_, I18n.format("menu.playdemo", new Object[0])));
		this.buttonResetDemo = addButton(new GuiButton(12, this.width / 2 - 100, p_addDemoButtons_1_ + p_addDemoButtons_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
		ISaveFormat isaveformat = this.mc.getSaveLoader();
		WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
		if (worldinfo == null) {
			this.buttonResetDemo.enabled = false;
		}
	}

	protected void actionPerformed(GuiButton p_actionPerformed_1_)
			throws IOException
	{
		if (p_actionPerformed_1_.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}
		if (p_actionPerformed_1_.id == 5) {
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}
		if (p_actionPerformed_1_.id == 1) {
			this.mc.displayGuiScreen(new GuiWorldSelection(this));
		}
		if (p_actionPerformed_1_.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		if ((p_actionPerformed_1_.id == 14) && (this.realmsButton.visible)) {
			switchToRealms();
		}
		if (p_actionPerformed_1_.id == 4) {
			this.mc.shutdown();
		}
		if (p_actionPerformed_1_.id == 6) {
			this.mc.displayGuiScreen(new GuiModList(this));
		}
		if (p_actionPerformed_1_.id == 11) {
			this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
		}
		if (p_actionPerformed_1_.id == 12)
		{
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
			if (worldinfo != null) {
				this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion", new Object[0]), "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]), I18n.format("selectWorld.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), 12));
			}
		}
	}

	private void switchToRealms()
	{
		RealmsBridge realmsbridge = new RealmsBridge();
		realmsbridge.switchToRealms(this);
	}

	public void confirmClicked(boolean p_confirmClicked_1_, int p_confirmClicked_2_)
	{
		if ((p_confirmClicked_1_) && (p_confirmClicked_2_ == 12))
		{
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			isaveformat.flushCache();
			isaveformat.deleteWorldDirectory("Demo_World");
			this.mc.displayGuiScreen(this);
		}
		else if (p_confirmClicked_2_ == 12)
		{
			this.mc.displayGuiScreen(this);
		}
		else if (p_confirmClicked_2_ == 13)
		{
			if (p_confirmClicked_1_) {
				try
				{
					Class<?> oclass = Class.forName("java.awt.Desktop");
					Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
					oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI(this.openGLWarningLink) });
				}
				catch (Throwable throwable)
				{
					LOGGER.error("Couldn't open link", throwable);
				}
			}
			this.mc.displayGuiScreen(this);
		}
	}

	private void drawPanorama(int p_drawPanorama_1_, int p_drawPanorama_2_, float p_drawPanorama_3_)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.matrixMode(5889);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
		GlStateManager.matrixMode(5888);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableCull();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		int i = 8;
		for (int j = 0; j < 64; j++)
		{
			GlStateManager.pushMatrix();
			float f = (j % 8 / 8.0F - 0.5F) / 64.0F;
			float f1 = (j / 8 / 8.0F - 0.5F) / 64.0F;
			float f2 = 0.0F;
			GlStateManager.translate(f, f1, 0.0F);
			GlStateManager.rotate(MathHelper.sin(this.panoramaTimer / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-this.panoramaTimer * 0.1F, 0.0F, 1.0F, 0.0F);
			for (int k = 0; k < 6; k++)
			{
				GlStateManager.pushMatrix();
				if (k == 1) {
					GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
				}
				if (k == 2) {
					GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				}
				if (k == 3) {
					GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
				}
				if (k == 4) {
					GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				}
				if (k == 5) {
					GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				}
				this.mc.getTextureManager().bindTexture(TITLE_PANORAMA_PATHS[k]);
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				int l = 255 / (j + 1);
				float f3 = 0.0F;
				bufferbuilder.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
				bufferbuilder.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
				bufferbuilder.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
				bufferbuilder.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
				tessellator.draw();
				GlStateManager.popMatrix();
			}
			GlStateManager.popMatrix();
			GlStateManager.colorMask(true, true, true, false);
		}
		bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.matrixMode(5889);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.enableDepth();
	}

	private void rotateAndBlurSkybox()
	{
		this.mc.getTextureManager().bindTexture(this.backgroundTexture);
		GlStateManager.glTexParameteri(3553, 10241, 9729);
		GlStateManager.glTexParameteri(3553, 10240, 9729);
		GlStateManager.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.colorMask(true, true, true, false);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		GlStateManager.disableAlpha();
		int i = 3;
		for (int j = 0; j < 3; j++)
		{
			float f = 1.0F / (j + 1);
			int k = this.width;
			int l = this.height;
			float f1 = (j - 1) / 256.0F;
			bufferbuilder.pos(k, l, this.zLevel).tex(0.0F + f1, 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
			bufferbuilder.pos(k, 0.0D, this.zLevel).tex(1.0F + f1, 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
			bufferbuilder.pos(0.0D, 0.0D, this.zLevel).tex(1.0F + f1, 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
			bufferbuilder.pos(0.0D, l, this.zLevel).tex(0.0F + f1, 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
		}
		tessellator.draw();
		GlStateManager.enableAlpha();
		GlStateManager.colorMask(true, true, true, true);
	}

	private void renderSkybox(int p_renderSkybox_1_, int p_renderSkybox_2_, float p_renderSkybox_3_)
	{
		this.mc.getFramebuffer().unbindFramebuffer();
		GlStateManager.viewport(0, 0, 256, 256);
		drawPanorama(p_renderSkybox_1_, p_renderSkybox_2_, p_renderSkybox_3_);
		rotateAndBlurSkybox();
		rotateAndBlurSkybox();
		rotateAndBlurSkybox();
		rotateAndBlurSkybox();
		rotateAndBlurSkybox();
		rotateAndBlurSkybox();
		rotateAndBlurSkybox();
		this.mc.getFramebuffer().bindFramebuffer(true);
		GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		float f = 120.0F / (this.width > this.height ? this.width : this.height);
		float f1 = this.height * f / 256.0F;
		float f2 = this.width * f / 256.0F;
		int i = this.width;
		int j = this.height;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(0.0D, j, this.zLevel).tex(0.5F - f1, 0.5F + f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		bufferbuilder.pos(i, j, this.zLevel).tex(0.5F - f1, 0.5F - f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		bufferbuilder.pos(i, 0.0D, this.zLevel).tex(0.5F + f1, 0.5F - f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, 0.0D, this.zLevel).tex(0.5F + f1, 0.5F + f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		tessellator.draw();
	}

	public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_)
	{
		this.panoramaTimer += p_drawScreen_3_;
		GlStateManager.disableAlpha();
		renderSkybox(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
		GlStateManager.enableAlpha();
		int i = 274;
		int j = this.width / 2 - 137;
		int k = 30;
		drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
		drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
		this.mc.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.minceraftRoll < 1.0E-4D)
		{
			drawTexturedModalRect(j + 0, 30, 0, 0, 99, 44);
			drawTexturedModalRect(j + 99, 30, 129, 0, 27, 44);
			drawTexturedModalRect(j + 99 + 26, 30, 126, 0, 3, 44);
			drawTexturedModalRect(j + 99 + 26 + 3, 30, 99, 0, 26, 44);
			drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
		}
		else
		{
			drawTexturedModalRect(j + 0, 30, 0, 0, 155, 44);
			drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
		}
		//TODO: this.splashText = ForgeHooksClient.renderMainMenu(getMainMenuFromThis(this), this.fontRenderer, this.width, this.height, this.splashText);

		GlStateManager.pushMatrix();
		GlStateManager.translate(this.width / 2 + 90, 70.0F, 0.0F);
		GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
		float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
		f = f * 100.0F / (this.fontRenderer.getStringWidth(this.splashText) + 32);
		GlStateManager.scale(f, f, f);
		drawCenteredString(this.fontRenderer, this.splashText, 0, -8, 65280);
		GlStateManager.popMatrix();
		String s = "Minecraft 1.12";
		if (this.mc.isDemo()) {
			s = s + " Demo";
		} else {
			s = s + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : new StringBuilder().append("/").append(this.mc.getVersionType()).toString());
		}
		List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
		for (int brdline = 0; brdline < brandings.size(); brdline++)
		{
			String brd = (String)brandings.get(brdline);
			if (!Strings.isNullOrEmpty(brd)) {
				drawString(this.fontRenderer, brd, 2, this.height - (10 + brdline * (this.fontRenderer.FONT_HEIGHT + 1)), 16777215);
			}
		}
		drawString(this.fontRenderer, "Copyright Mojang AB. Do not distribute!", this.widthCopyrightRest, this.height - 10, -1);
		
		this.drawString(this.fontRenderer, Language.TITLE_INSTALLED, this.width - this.fontRenderer.getStringWidth(Language.TITLE_INSTALLED) - 2, this.height - 20, -1);
		
		if ((p_drawScreen_1_ > this.widthCopyrightRest) && (p_drawScreen_1_ < this.widthCopyrightRest + this.widthCopyright) && (p_drawScreen_2_ > this.height - 10) && (p_drawScreen_2_ < this.height) && (Mouse.isInsideWindow())) {
			drawRect(this.widthCopyrightRest, this.height - 1, this.widthCopyrightRest + this.widthCopyright, this.height, -1);
		}
		if ((this.openGLWarning1 != null) && (!this.openGLWarning1.isEmpty()))
		{
			drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
			drawString(this.fontRenderer, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
			drawString(this.fontRenderer, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, ((GuiButton)this.buttonList.get(0)).y - 12, -1);
		}
		super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
		if (areRealmsNotificationsEnabled()) {
			this.realmsNotification.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
		}
		//TODO: this.modUpdateNotification.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
	}

	protected void mouseClicked(int p_mouseClicked_1_, int p_mouseClicked_2_, int p_mouseClicked_3_)
			throws IOException
	{
		super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_2_, p_mouseClicked_3_);
		synchronized (this.threadLock)
		{
			if ((!this.openGLWarning1.isEmpty()) && (!StringUtils.isNullOrEmpty(this.openGLWarningLink)) && (p_mouseClicked_1_ >= this.openGLWarningX1) && (p_mouseClicked_1_ <= this.openGLWarningX2) && (p_mouseClicked_2_ >= this.openGLWarningY1) && (p_mouseClicked_2_ <= this.openGLWarningY2))
			{
				GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
				guiconfirmopenlink.disableSecurityWarning();
				this.mc.displayGuiScreen(guiconfirmopenlink);
			}
		}
		if (areRealmsNotificationsEnabled()) {
			//this.realmsNotification.mouseClicked(p_mouseClicked_1_, p_mouseClicked_2_, p_mouseClicked_3_); //TODO: FIX
		}
		if ((p_mouseClicked_1_ > this.widthCopyrightRest) && (p_mouseClicked_1_ < this.widthCopyrightRest + this.widthCopyright) && (p_mouseClicked_2_ > this.height - 10) && (p_mouseClicked_2_ < this.height)) {
			this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
		}
	}

	public void onGuiClosed()
	{
		if (this.realmsNotification != null) {
			this.realmsNotification.onGuiClosed();
		}
	}
	
	/*private GuiMainMenu getMainMenuFromThis(GuiNewMainMenu guiNewMainMenu) {
		Object o = guiNewMainMenu;
		return (GuiMainMenu)o;
	}*/
}
