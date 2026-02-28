package com.daqem.knot.events.mixin.common.entity;

import com.daqem.knot.events.common.entity.EntityEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @WrapOperation(
            method = "setLevelCallback",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/Entity;levelCallback:Lnet/minecraft/world/level/entity/EntityInLevelCallback;",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void knot$wrapLevelCallback(Entity instance, EntityInLevelCallback callback, Operation<Void> original) {
        if (callback == null || callback == EntityInLevelCallback.NULL) {
            original.call(instance, callback);
            return;
        }

        Entity self = (Entity) (Object) this;

        EntityInLevelCallback wrapped = new EntityInLevelCallback() {
            private long lastSection = SectionPos.asLong(self.blockPosition());

            @Override
            public void onMove() {
                callback.onMove();
                long newSection = SectionPos.asLong(self.blockPosition());
                if (lastSection != newSection) {
                    EntityEvent.ENTER_SECTION.invoker().onEnterSection(self, lastSection, newSection);
                    lastSection = newSection;
                }
            }

            @Override
            public void onRemove(Entity.@NotNull RemovalReason reason) {
                callback.onRemove(reason);
            }
        };

        original.call(instance, wrapped);
    }
}