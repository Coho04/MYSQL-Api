# MYSQL-Api
JDA (Java Discord API)
JDA strives to provide a clean and full wrapping of the Discord REST api and its Websocket-Events for Java. This library is a helpful tool that provides the functionality to create a discord bot in java.

Summary
Due to official statements made by the Discord developers we will no longer support unofficial features. These features are undocumented API endpoints or protocols that are not available to bot-accounts.

Please see the Discord docs for more information about bot accounts.

Introduction
Sharding
Entity Lifetimes
Download
Documentation
Support
Extensions And Plugins
Contributing
Dependencies
Other Libraries
UserBots and SelfBots
Discord is currently prohibiting creation and usage of automated client accounts (AccountType.CLIENT). We have officially dropped support for client login as of version 4.2.0! Note that JDA is not a good tool to build a custom discord client as it loads all servers/guilds on startup unlike a client which does this via lazy loading instead. If you need a bot, use a bot account from the Application Dashboard.

Read More

Creating the JDA Object
Creating the JDA Object is done via the JDABuilder class. After setting the token and other options via setters, the JDA Object is then created by calling the build() method. When build() returns, JDA might not have finished starting up. However, you can use awaitReady() on the JDA object to ensure that the entire cache is loaded before proceeding. Note that this method is blocking and will cause the thread to sleep until startup has completed.

Example:

JDA jda = JDABuilder.createDefault("token").build();
Configuration
Both the JDABuilder and the DefaultShardManagerBuilder allow a set of configurations to improve the experience.

Example:

public static void main(String[] args) {
    JDABuilder builder = JDABuilder.createDefault(args[0]);
    
    // Disable parts of the cache
    builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
    // Enable the bulk delete event
    builder.setBulkDeleteSplittingEnabled(false);
    // Disable compression (not recommended)
    builder.setCompression(Compression.NONE);
    // Set activity (like "playing Something")
    builder.setActivity(Activity.watching("TV"));
    
    builder.build();
}
See JDABuilder and DefaultShardManagerBuilder

You can configure the memory usage by changing enabled CacheFlags on the JDABuilder. Additionally, you can change the handling of member/user cache by setting either a ChunkingFilter, disabling intents, or changing the member cache policy.

public void configureMemoryUsage(JDABuilder builder) {
    // Disable cache for member activities (streaming/games/spotify)
    builder.disableCache(CacheFlag.ACTIVITY);

    // Only cache members who are either in a voice channel or owner of the guild
    builder.setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER));

    // Disable member chunking on startup
    builder.setChunkingFilter(ChunkingFilter.NONE);

    // Disable presence updates and typing events
    builder.disableIntents(GatewayIntent.GUILD_PRESENCE, GatewayIntent.GUILD_MESSAGE_TYPING);

    // Consider guilds with more than 50 members as "large". 
    // Large guilds will only provide online members in their setup and thus reduce bandwidth if chunking is disabled.
    builder.setLargeThreshold(50);
}
Listening to Events
The event system in JDA is configured through a hierarchy of classes/interfaces. We offer two implementations for the IEventManager:

InterfacedEventManager which uses an EventListener interface and the ListenerAdapter abstract class
AnnotatedEventManager which uses the @SubscribeEvent annotation that can be applied to methods
By default the InterfacedEventManager is used. Since you can create your own implementation of IEventManager this is a very versatile and configurable system. If the aforementioned implementations don't suit your use-case you can simply create a custom implementation and configure it on the JDABuilder with setEventManager(...).

Examples:
Using EventListener:

public class ReadyListener implements EventListener
{
    public static void main(String[] args)
            throws LoginException, InterruptedException
    {
        // Note: It is important to register your ReadyListener before building
        JDA jda = JDABuilder.createDefault("token")
            .addEventListeners(new ReadyListener())
            .build();

        // optionally block until JDA is ready
        jda.awaitReady();
    }

    @Override
    public void onEvent(GenericEvent event)
    {
        if (event instanceof ReadyEvent)
            System.out.println("API is ready!");
    }
}
Using ListenerAdapter:

public class MessageListener extends ListenerAdapter
{
    public static void main(String[] args)
            throws LoginException
    {
        JDA jda = JDABuilder.createDefault("token").build();
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        jda.addEventListener(new MessageListener());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                                    event.getMessage().getContentDisplay());
        }
        else
        {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                        event.getTextChannel().getName(), event.getMember().getEffectiveName(),
                        event.getMessage().getContentDisplay());
        }
    }
}
Ping-Pong Bot:

public class Bot extends ListenerAdapter
{
    public static void main(String[] args) throws LoginException
    {
        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }
        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
            .addEventListeners(new Bot())
            .setActivity(Activity.playing("Type !ping"))
            .build();
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                   .queue(response /* => Message */ -> {
                       response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                   });
        }
    }
}
Slash-Commands:

public class Bot extends ListenerAdapter
{
    public static void main(String[] args) throws LoginException
    {
        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }
        // args[0] should be the token
        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createLight(args[0], Collections.emptyList())
            .addEventListeners(new Bot())
            .setActivity(Activity.playing("Type /ping"))
            .build();

        jda.upsertCommand("ping", "Calculate ping of the bot").queue(); // This can take up to 1 hour to show up in the client
    }
    
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        if (!event.getName().equals("ping")) return; // make sure we handle the right command
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true) // reply or acknowledge
             .flatMap(v ->
                 event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
             ).queue(); // Queue both reply and edit
    }
}
RestAction
Through RestAction we provide request handling with

callbacks
promises
and sync
and it is up to the user to decide which pattern to utilize. It can be combined with reactive libraries such as reactor-core due to being lazy.

The RestAction interface also supports a number of operators to avoid callback hell:

map Convert the result of the RestAction to a different value
flatMap Chain another RestAction on the result
delay Delay the element of the previous step
Example:

public RestAction<Void> selfDestruct(MessageChannel channel, String content) {
    return channel.sendMessage("The following message will destroy itself in 1 minute!")
        .delay(10, SECONDS, scheduler) // edit 10 seconds later
        .flatMap((it) -> it.editMessage(content))
        .delay(1, MINUTES, scheduler) // delete 1 minute later
        .flatMap(Message::delete);
}
More Examples
We provide a small set of Examples in the Example Directory.

Sharding a Bot
Discord allows Bot-accounts to share load across sessions by limiting them to a fraction of the total connected Guilds/Servers of the bot.
This can be done using sharding which will limit JDA to only a certain amount of Guilds/Servers including events and entities. Sharding will limit the amount of Guilds/Channels/Users visible to the JDA session so it is recommended to have some kind of elevated management to access information of other shards.

To use sharding in JDA you will need to use JDABuilder.useSharding(int shardId, int shardTotal). The shardId is 0-based which means the first shard has the ID 0. The shardTotal is the total amount of shards (not 0-based) which can be seen similar to the length of an array, the last shard has the ID of shardTotal - 1.

The SessionController is a tool of the JDABuilder that allows to control state and behaviour between shards (sessions). When using multiple builders to build shards you have to create one instance of this controller and add the same instance to each builder: builder.setSessionController(controller)

Since version 3.4.0 JDA provides a ShardManager which automates this building process.

Example Sharding - Using JDABuilder
public static void main(String[] args) throws Exception
{
    JDABuilder shardBuilder = JDABuilder.createDefault(args[0]);
    //register your listeners here using shardBuilder.addEventListeners(...)
    shardBuilder.addEventListeners(new MessageListener());
    for (int i = 0; i < 10; i++)
    {
        shardBuilder.useSharding(i, 10)
                    .build();
    }
}
When the useSharding method is invoked for the first time, the builder automatically sets a SessionController internally (if none is present)

Example Sharding - Using DefaultShardManager
public static void main(String[] args) throws Exception
{
    DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(args[0]);
    builder.addEventListeners(new MessageListener());
    builder.build();
}
Entity Lifetimes
An Entity is the term used to describe types such as GuildChannel/Message/User and other entities that Discord provides. Instances of these entities are created and deleted by JDA when Discord instructs it. This means the lifetime depends on signals provided by the Discord API which are used to create/update/delete entities. This is done through Gateway Events known as "dispatches" that are handled by the JDA WebSocket handlers. When Discord instructs JDA to delete entities, they are simply removed from the JDA cache and lose their references. Once that happens, nothing in JDA interacts or updates the instances of those entities, and they become useless. Discord may instruct to delete these entities randomly for cache synchronization with the API.

It is not recommended to store any of these entities for a longer period of time! Instead of keeping (e.g.) a User instance in some field, an ID should be used. With the ID of a user, you can use getUserById(id) to get and keep the user reference in a local variable (see below).

Entity Updates
When an entity is updated through its manager, they will send a request to the Discord API which will update the state of the entity. The success of this request does not imply the entity has been updated yet. All entities are updated by the aforementioned Gateway Events which means you cannot rely on the cache being updated yet once the execution of a RestAction has completed. Some requests rely on the cache being updated to correctly update the entity. An example of this is updating roles of a member which overrides all roles of the member by sending a list of the new set of roles. This is done by first checking the current cache, the roles the member has right now, and appending or removing the requested roles. If the cache has not yet been updated by an event, this will result in unexpected behavior.

Entity Deletion
Discord may request that a client (the JDA session) invalidates its entire cache. When this happens, JDA will remove all of its current entities and reconnect the session. This is signaled through the ReconnectEvent. When entities are removed from the JDA cache, they lose access to the encapsulating entities. For instance, a channel loses access to its guild. Once that happens, they are unable to make any API requests through RestAction and instead throw an IllegalStateException. It is highly recommended to only keep references to entities by storing their id and using the respective get...ById(id) method when needed.

Example
public class UserLogger extends ListenerAdapter 
{
    private final long userId;
    
    public UserLogger(User user)
    {
        this.userId = user.getIdLong();
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        User author = event.getAuthor();
        Message message = event.getMessage();
        if (author.getIdLong() == userId)
        {
            // Print the message of the user
            System.out.println(author.getAsTag() + ": " + message.getContentDisplay());
        }
    }
    
    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        JDA api = event.getJDA();
        User user = api.getUserById(userId); // Acquire a reference to the User instance through the id
        user.openPrivateChannel().queue((channel) ->
        {
            // Send a private message to the user
            channel.sendMessageFormat("I have joined a new guild: **%s**", event.getGuild().getName()).queue();
        });
    }
}
Download
maven-central jitpack

Latest Release: GitHub Release

Be sure to replace the VERSION key below with the one of the versions shown above! For snapshots, please use the instructions provided by JitPack.

Maven

<dependency>
    <groupId>net.dv8tion</groupId>
    <artifactId>JDA</artifactId>
    <version>VERSION</version>
</dependency>
Maven without Audio

<dependency>
    <groupId>net.dv8tion</groupId>
    <artifactId>JDA</artifactId>
    <version>VERSION</version>
    <exclusions>
        <exclusion>
            <groupId>club.minnced</groupId>
            <artifactId>opus-java</artifactId>
        </exclusion>
    </exclusions>
</dependency>
Gradle

repositories {
    mavenCentral()
}

dependencies {
    //Change 'implementation' to 'compile' in old Gradle versions
    implementation("net.dv8tion:JDA:VERSION")
}
Gradle without Audio

dependencies {
    //Change 'implementation' to 'compile' in old Gradle versions
    implementation("net.dv8tion:JDA:VERSION") {
        exclude module: 'opus-java'
    }
}
The snapshot builds are only available via JitPack and require adding the JitPack resolver, you need to specify specific commits to access those builds. Stable releases are published to maven-central.

If you do not need any opus de-/encoding done by JDA (voice receive/send with PCM) you can exclude opus-java entirely. This can be done if you only send audio with an AudioSendHandler which only sends opus (isOpus() = true). (See lavaplayer)

If you want to use a custom opus library you can provide the absolute path to OpusLibrary.loadFrom(String) before using the audio api of JDA. This works without opus-java-natives as it only requires opus-java-api.
For this setup you should only exclude opus-java-natives as opus-java-api is a requirement for en-/decoding.

See opus-java

Logging Framework - SLF4J
JDA is using SLF4J to log its messages.

That means you should add some SLF4J implementation to your build path in addition to JDA. If no implementation is found, following message will be printed to the console on startup:

SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
JDA currently provides a fallback Logger in case that no SLF4J implementation is present. We strongly recommend to use one though, as that can improve speed and allows you to customize the Logger as well as log to files

There is a guide for logback-classic available in our wiki: Logging Setup

Documentation
Docs can be found on the Jenkins or directly here
A simple Wiki can also be found in this repository's Wiki section

Annotations
We use a number of annotations to indicate future plans for implemented functionality such as new features of the Discord API.

Incubating
This annotation is used to indicate that functionality may change in the future. Often used when a new feature is added.
ReplaceWith
Paired with @Deprecated this is used to inform you how the new code-fragment is supposed to look once the hereby annotated functionality is removed.
ForRemoval
Paired with @Deprecated this indicates that we plan to entirely remove the hereby annotated functionality in the future.
DeprecatedSince
Paired with @Deprecated this specifies when a feature was marked as deprecated.
Sources

Getting Help
For general troubleshooting you can visit our wiki Troubleshooting and FAQ.
If you need help, or just want to talk with the JDA or other Devs, you can join the Official JDA Discord Guild.

Alternatively you can also join the Unofficial Discord API Guild. Once you joined, you can find JDA-specific help in the #java_jda channel.

For guides and setup help you can also take a look at the wiki
Especially interesting are the Getting Started and Setup Pages.

Third Party Recommendations
LavaPlayer
Created and maintained by sedmelluq
LavaPlayer is the most popular library used by Music Bots created in Java. It is highly compatible with JDA and Discord4J and allows to play audio from Youtube, Soundcloud, Twitch, Bandcamp and more providers.
The library can easily be expanded to more services by implementing your own AudioSourceManager and registering it.

It is recommended to read the Usage section of LavaPlayer to understand a proper implementation.
Sedmelluq provided a demo in his repository which presents an example implementation for JDA: https://github.com/sedmelluq/lavaplayer/tree/master/demo-jda

Lavalink
Maintained by Freya Arbjerg.

Lavalink is a popular standalone audio sending node based on Lavaplayer. Lavalink was built with scalability in mind, and allows streaming music via many servers. It supports most of Lavaplayer's features.

Lavalink is used by many large bots, as well as bot developers who can not use a Java library like Lavaplayer. If you plan on serving music on a smaller scale with JDA it is often preferable to just use Lavaplayer directly as it is easier.

Lavalink-Client is the official Lavalink client for JDA.

jda-nas
Created and maintained by sedmelluq
Provides a native implementation for the JDA Audio Send-System to avoid GC pauses.

Note that this send system creates an extra UDP-Client which causes audio receive to no longer function properly since discord identifies the sending UDP-Client as the receiver.

JDABuilder builder = JDABuilder.createDefault(BOT_TOKEN)
    .setAudioSendFactory(new NativeAudioSendFactory());
jda-ktx
Created and maintained by MinnDevelopment.
Provides Kotlin extensions for RestAction and events that provide a more idiomatic Kotlin experience.

fun main() {
    val jda = light(BOT_TOKEN)
    
    jda.onCommand("ping") { event ->
        val time = measureTime {
            event.reply("Pong!").await() // suspending
        }.inWholeMilliseconds

        event.hook.editOriginal("Pong: $time ms").queue()
    }
}
There is a number of examples available in the README.

More can be found in our github organization: JDA-Applications

Contributing to JDA
If you want to contribute to JDA, make sure to base your branch off of our development branch (or a feature-branch) and create your PR into that same branch. We will be rejecting any PRs between branches or into release branches! It is very possible that your change might already be in development or you missed something.

More information can be found at the wiki page Contributing

Deprecation Policy
When a feature is introduced to replace or enhance existing functionality we might deprecate old functionality.

A deprecated method/class usually has a replacement mentioned in its documentation which should be switched to. Deprecated functionality might or might not exist in the next minor release. (Hint: The minor version is the MM of XX.MM.RR in our version format)

It is possible that some features are deprecated without replacement, in this case the functionality is no longer supported by either the JDA structure due to fundamental changes (for example automation of a feature) or due to Discord API changes that cause it to be removed.

We highly recommend discontinuing usage of deprecated functionality and update by going through each minor release instead of jumping. For instance, when updating from version 3.3.0 to version 3.5.1 you should do the following:

Update to 3.4.RR and check for deprecation, replace
Update to 3.5.1 and check for deprecation, replace
The RR in version 3.4.RR should be replaced by the latest version that was published for 3.4, you can find out which the latest version was by looking at the release page

Dependencies:
This project requires Java 8+.
All dependencies are managed automatically by Gradle.

NV Websocket Client
Version: 2.14
Github
OkHttp
Version: 3.13.0
Github
Apache Commons Collections4
Version: 4.1
Website
jackson
Version: 2.10.1
Github
Trove4j
Version: 3.0.3
BitBucket
slf4j-api
Version: 1.7.25
Website
opus-java (optional)
Version: 1.1.1
GitHub
Related Projects
Discord4J
Discord.NET
discord.py
serenity
See also: https://discord.com/developers/docs/topics/community-resources#libraries
