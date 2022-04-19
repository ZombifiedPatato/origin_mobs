package net.zombified_patato.origin_mobs.entity.client;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TntMinecartEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.zombified_patato.origin_mobs.OriginMobs;
import net.zombified_patato.origin_mobs.block.ModBlocks;
import net.zombified_patato.origin_mobs.entity.custom.DwarvenDynamiteEntity;

public class DwarvenDynamiteRenderer extends EntityRenderer<DwarvenDynamiteEntity> {

    public DwarvenDynamiteRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(DwarvenDynamiteEntity dwarvenDynamiteEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.5, 0.0);
        int j = dwarvenDynamiteEntity.getFuse();
        if ((float)j - g + 1.0f < 10.0f) {
            float h = 1.0f - ((float)j - g + 1.0f) / 10.0f;
            h = MathHelper.clamp(h, 0.0f, 1.0f);
            h *= h;
            h *= h;
            float k = 1.0f + h * 0.3f;
            matrixStack.scale(k, k, k);
        }
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0f));
        matrixStack.translate(-0.5, -0.5, 0.5);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
        TntMinecartEntityRenderer.renderFlashingBlock(ModBlocks.DWARVEN_DYNAMITE_BLOCK.getDefaultState(), matrixStack, vertexConsumerProvider, i, j / 5 % 2 == 0);
        matrixStack.pop();
        super.render(dwarvenDynamiteEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(DwarvenDynamiteEntity entity) {
        return new Identifier(OriginMobs.MOD_ID, "textures/entity/dwarven_dynamite/dwarven_dynamite.png");
    }
}