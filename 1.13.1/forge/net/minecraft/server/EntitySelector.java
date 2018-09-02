package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class EntitySelector {
    private final int a;
    private final boolean b;
    private final boolean c;
    private final Predicate<Entity> d;
    private final CriterionConditionValue.c e;
    private final Function<Vec3D, Vec3D> f;
    @Nullable
    private final AxisAlignedBB g;
    private final BiConsumer<Vec3D, List<? extends Entity>> h;
    private final boolean i;
    @Nullable
    private final String j;
    @Nullable
    private final UUID k;
    private final Class<? extends Entity> l;
    private final boolean m;

    public EntitySelector(int ix, boolean flag, boolean flag1, Predicate<Entity> predicate, CriterionConditionValue.c criterionconditionvalue$c, Function<Vec3D, Vec3D> function, @Nullable AxisAlignedBB axisalignedbb, BiConsumer<Vec3D, List<? extends Entity>> biconsumer, boolean flag2, @Nullable String s, @Nullable UUID uuid, Class<? extends Entity> oclass, boolean flag3) {
        this.a = ix;
        this.b = flag;
        this.c = flag1;
        this.d = predicate;
        this.e = criterionconditionvalue$c;
        this.f = function;
        this.g = axisalignedbb;
        this.h = biconsumer;
        this.i = flag2;
        this.j = s;
        this.k = uuid;
        this.l = oclass;
        this.m = flag3;
    }

    public int a() {
        return this.a;
    }

    public boolean b() {
        return this.b;
    }

    public boolean c() {
        return this.i;
    }

    public boolean d() {
        return this.c;
    }

    private void e(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
        if (this.m && !commandlistenerwrapper.hasPermission(2)) {
            throw ArgumentEntity.f.create();
        }
    }

    public Entity a(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
        this.e(commandlistenerwrapper);
        List list = this.b(commandlistenerwrapper);
        if (list.isEmpty()) {
            throw ArgumentEntity.d.create();
        } else if (list.size() > 1) {
            throw ArgumentEntity.a.create();
        } else {
            return (Entity)list.get(0);
        }
    }

    public List<? extends Entity> b(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
        this.e(commandlistenerwrapper);
        if (!this.b) {
            return this.d(commandlistenerwrapper);
        } else if (this.j != null) {
            EntityPlayer entityplayer = commandlistenerwrapper.getServer().getPlayerList().getPlayer(this.j);
            return (List<? extends Entity>)(entityplayer == null ? Collections.emptyList() : Lists.newArrayList(new EntityPlayer[]{entityplayer}));
        } else if (this.k != null) {
            for(WorldServer worldserver1 : commandlistenerwrapper.getServer().getWorlds()) {
                Entity entity = worldserver1.getEntity(this.k);
                if (entity != null) {
                    return Lists.newArrayList(new Entity[]{entity});
                }
            }

            return Collections.emptyList();
        } else {
            Vec3D vec3d = (Vec3D)this.f.apply(commandlistenerwrapper.getPosition());
            Predicate predicate = this.a(vec3d);
            if (this.i) {
                return (List<? extends Entity>)(commandlistenerwrapper.f() != null && predicate.test(commandlistenerwrapper.f()) ? Lists.newArrayList(new Entity[]{commandlistenerwrapper.f()}) : Collections.emptyList());
            } else {
                ArrayList arraylist = Lists.newArrayList();
                if (this.d()) {
                    this.a(arraylist, commandlistenerwrapper.getWorld(), vec3d, predicate);
                } else {
                    for(WorldServer worldserver : commandlistenerwrapper.getServer().getWorlds()) {
                        this.a(arraylist, worldserver, vec3d, predicate);
                    }
                }

                return this.a(vec3d, arraylist);
            }
        }
    }

    private void a(List<Entity> list, WorldServer worldserver, Vec3D vec3d, Predicate<Entity> predicate) {
        if (this.g != null) {
            list.addAll(worldserver.a(this.l, this.g.a(vec3d), predicate::test));
        } else {
            list.addAll(worldserver.a(this.l, predicate::test));
        }

    }

    public EntityPlayer c(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
        this.e(commandlistenerwrapper);
        List list = this.d(commandlistenerwrapper);
        if (list.size() != 1) {
            throw ArgumentEntity.e.create();
        } else {
            return (EntityPlayer)list.get(0);
        }
    }

    public List<EntityPlayer> d(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
        this.e(commandlistenerwrapper);
        if (this.j != null) {
            EntityPlayer entityplayer2 = commandlistenerwrapper.getServer().getPlayerList().getPlayer(this.j);
            return (List<EntityPlayer>)(entityplayer2 == null ? Collections.emptyList() : Lists.newArrayList(new EntityPlayer[]{entityplayer2}));
        } else if (this.k != null) {
            EntityPlayer entityplayer1 = commandlistenerwrapper.getServer().getPlayerList().a(this.k);
            return (List<EntityPlayer>)(entityplayer1 == null ? Collections.emptyList() : Lists.newArrayList(new EntityPlayer[]{entityplayer1}));
        } else {
            Vec3D vec3d = (Vec3D)this.f.apply(commandlistenerwrapper.getPosition());
            Predicate predicate = this.a(vec3d);
            if (this.i) {
                if (commandlistenerwrapper.f() instanceof EntityPlayer) {
                    EntityPlayer entityplayer3 = (EntityPlayer)commandlistenerwrapper.f();
                    if (predicate.test(entityplayer3)) {
                        return Lists.newArrayList(new EntityPlayer[]{entityplayer3});
                    }
                }

                return Collections.emptyList();
            } else {
                Object object;
                if (this.d()) {
                    object = commandlistenerwrapper.getWorld().b(EntityPlayer.class, predicate::test);
                } else {
                    object = Lists.newArrayList();

                    for(EntityPlayer entityplayer : commandlistenerwrapper.getServer().getPlayerList().v()) {
                        if (predicate.test(entityplayer)) {
                            ((List)object).add(entityplayer);
                        }
                    }
                }

                return this.a(vec3d, (List)object);
            }
        }
    }

    private Predicate<Entity> a(Vec3D vec3d) {
        Predicate predicate = this.d;
        if (this.g != null) {
            AxisAlignedBB axisalignedbb = this.g.a(vec3d);
            predicate = predicate.and((entity) -> {
                return axisalignedbb.c(entity.getBoundingBox());
            });
        }

        if (!this.e.c()) {
            predicate = predicate.and((entity) -> {
                return this.e.a(entity.a(vec3d));
            });
        }

        return predicate;
    }

    private <T extends Entity> List<T> a(Vec3D vec3d, List<T> list) {
        if (list.size() > 1) {
            this.h.accept(vec3d, list);
        }

        return list.subList(0, Math.min(this.a, list.size()));
    }

    public static IChatBaseComponent a(List<? extends Entity> list) {
        return ChatComponentUtils.b(list, Entity::getScoreboardDisplayName);
    }
}