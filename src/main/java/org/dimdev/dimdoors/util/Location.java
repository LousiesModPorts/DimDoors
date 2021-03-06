package org.dimdev.dimdoors.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.dimdev.dimdoors.DimensionalDoorsInitializer;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class Location {
    public static final Codec<Location> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            World.CODEC.fieldOf("world").forGetter(location -> location.world),
            BlockPos.CODEC.fieldOf("pos").forGetter(location -> location.pos)
    ).apply(instance, Location::new));

    public final RegistryKey<World> world;
    public final BlockPos pos;

    public Location(RegistryKey<World> world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    public Location(ServerWorld world, int x, int y, int z) {
        this(world, new BlockPos(x, y, z));
    }

    public Location(ServerWorld world, BlockPos pos) {
        this(world.getRegistryKey(), pos);
    }

    public int getX() {
        return this.pos.getX();
    }

    public int getY() {
        return this.pos.getY();
    }

    public int getZ() {
        return this.pos.getZ();
    }

    public BlockState getBlockState() {
        return this.getWorld().getBlockState(this.pos);
    }

    public FluidState getFluidState() {
        return this.getWorld().getFluidState(this.pos);
    }

    public BlockEntity getBlockEntity() {
        return this.getWorld().getBlockEntity(this.pos);
    }

    public BlockPos getBlockPos() {
        return this.pos;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Location &&
                ((Location) obj).world.equals(this.world) &&
                ((Location) obj).pos.equals(this.pos);
    }

    @Override
    public int hashCode() {
        return this.world.hashCode() * 31 + this.pos.hashCode();
    }

    public RegistryKey<World> getWorldId() {
        return this.world;
    }

    public ServerWorld getWorld() {
        return DimensionalDoorsInitializer.getServer().getWorld(this.world);
    }
}
