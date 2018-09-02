package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import javax.annotation.Nullable;

public class IScoreboardCriteria {
    public static final Map<String, IScoreboardCriteria> criteria = Maps.newHashMap();
    public static final IScoreboardCriteria b = new IScoreboardCriteria("dummy");
    public static final IScoreboardCriteria c = new IScoreboardCriteria("trigger");
    public static final IScoreboardCriteria d = new IScoreboardCriteria("deathCount");
    public static final IScoreboardCriteria e = new IScoreboardCriteria("playerKillCount");
    public static final IScoreboardCriteria f = new IScoreboardCriteria("totalKillCount");
    public static final IScoreboardCriteria g = new IScoreboardCriteria("health", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.HEARTS);
    public static final IScoreboardCriteria h = new IScoreboardCriteria("food", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
    public static final IScoreboardCriteria i = new IScoreboardCriteria("air", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
    public static final IScoreboardCriteria j = new IScoreboardCriteria("armor", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
    public static final IScoreboardCriteria k = new IScoreboardCriteria("xp", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
    public static final IScoreboardCriteria l = new IScoreboardCriteria("level", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
    public static final IScoreboardCriteria[] m = new IScoreboardCriteria[]{new IScoreboardCriteria("teamkill." + EnumChatFormat.BLACK.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.DARK_BLUE.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.DARK_GREEN.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.DARK_AQUA.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.DARK_RED.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.DARK_PURPLE.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.GOLD.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.GRAY.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.DARK_GRAY.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.BLUE.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.GREEN.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.AQUA.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.RED.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.LIGHT_PURPLE.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.YELLOW.g()), new IScoreboardCriteria("teamkill." + EnumChatFormat.WHITE.g())};
    public static final IScoreboardCriteria[] n = new IScoreboardCriteria[]{new IScoreboardCriteria("killedByTeam." + EnumChatFormat.BLACK.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.DARK_BLUE.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.DARK_GREEN.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.DARK_AQUA.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.DARK_RED.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.DARK_PURPLE.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.GOLD.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.GRAY.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.DARK_GRAY.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.BLUE.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.GREEN.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.AQUA.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.RED.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.LIGHT_PURPLE.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.YELLOW.g()), new IScoreboardCriteria("killedByTeam." + EnumChatFormat.WHITE.g())};
    private final String o;
    private final boolean p;
    private final IScoreboardCriteria.EnumScoreboardHealthDisplay q;

    public IScoreboardCriteria(String s) {
        this(s, false, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
    }

    protected IScoreboardCriteria(String s, boolean flag, IScoreboardCriteria.EnumScoreboardHealthDisplay iscoreboardcriteria$enumscoreboardhealthdisplay) {
        this.o = s;
        this.p = flag;
        this.q = iscoreboardcriteria$enumscoreboardhealthdisplay;
        criteria.put(s, this);
    }

    @Nullable
    public static IScoreboardCriteria a(String s) {
        if (criteria.containsKey(s)) {
            return (IScoreboardCriteria)criteria.get(s);
        } else {
            int ix = s.indexOf(58);
            if (ix < 0) {
                return null;
            } else {
                StatisticWrapper statisticwrapper = IRegistry.STATS.get(MinecraftKey.a(s.substring(0, ix), '.'));
                return statisticwrapper == null ? null : a(statisticwrapper, MinecraftKey.a(s.substring(ix + 1), '.'));
            }
        }
    }

    @Nullable
    private static <T> IScoreboardCriteria a(StatisticWrapper<T> statisticwrapper, MinecraftKey minecraftkey) {
        IRegistry iregistry = statisticwrapper.a();
        return iregistry.c(minecraftkey) ? statisticwrapper.b(iregistry.get(minecraftkey)) : null;
    }

    public String getName() {
        return this.o;
    }

    public boolean isReadOnly() {
        return this.p;
    }

    public IScoreboardCriteria.EnumScoreboardHealthDisplay e() {
        return this.q;
    }

    public static enum EnumScoreboardHealthDisplay {
        INTEGER("integer"),
        HEARTS("hearts");

        private final String c;
        private static final Map<String, IScoreboardCriteria.EnumScoreboardHealthDisplay> d;

        private EnumScoreboardHealthDisplay(String s1) {
            this.c = s1;
        }

        public String a() {
            return this.c;
        }

        public static IScoreboardCriteria.EnumScoreboardHealthDisplay a(String s) {
            return (IScoreboardCriteria.EnumScoreboardHealthDisplay)d.getOrDefault(s, INTEGER);
        }

        static {
            Builder builder = ImmutableMap.builder();

            for(IScoreboardCriteria.EnumScoreboardHealthDisplay iscoreboardcriteria$enumscoreboardhealthdisplay : values()) {
                builder.put(iscoreboardcriteria$enumscoreboardhealthdisplay.c, iscoreboardcriteria$enumscoreboardhealthdisplay);
            }

            d = builder.build();
        }
    }
}