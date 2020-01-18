var config = {
    title: "Starfall",
    width: 800,
    height: 600,
    parent: "game",
    backgroundColor: "#18216D",
    physics: {
        default: 'arcade',
        arcade: {
            debug: false,
            gravity: { y: 300 }
        }
    },
    scene: {
        preload: preload,
        create: create,
        update: update
    }
};

var game = new Phaser.Game(config);
var platforms;

function preload() {
    //TODO fix me
    this.load.image('ship', 'resources/assets/spaceShips_001.png');
    this.load.image('otherPlayer', 'resources/assets/enemyBlack5.png');
    this.load.image('star', 'resources/assets/star_gold.png');
    this.load.image('ground', 'resources/assets/platform.png');

    this.load.spritesheet('dude',
        'resources/assets/dude.png',
        { frameWidth: 32, frameHeight: 48 }
    );
}


function create(){
    var self = this;

    this.playersArray = {};

    // platforms
    this.platforms = this.physics.add.staticGroup();
    this.platforms.create(400, 568, 'ground').setScale(2).refreshBody();

    this.platforms.create(600, 400, 'ground');
    this.platforms.create(50, 250, 'ground');
    this.platforms.create(750, 220, 'ground');

    this.cursors = this.input.keyboard.createCursorKeys();
    this.otherPlayers = this.physics.add.group();

    setup(self);


    addPlayer(self);

    //var jsonObject = {userName: this.playerUUID,
    //    message: "moj custom"};
    //self.socket.emit('mine_event', jsonObject);

    //requestPosition(self);
}


function update(){
    if (this.player) {

        // controlls

        // emit new players movment

        // save old position

        if (this.cursors.left.isDown) {
            this.player.setVelocityX(-160);
            this.player.anims.play('left', true);

        } else if (this.cursors.right.isDown) {

            this.player.setVelocityX(160);
            this.player.anims.play('right', true);

        } else {

            this.player.setVelocityX(0);
            this.player.anims.play('turn');

        }

        if (this.cursors.up.isDown && this.player.body.touching.down)
        {
            this.player.setVelocityY(-330);
        }

        //this.physics.world.wrap(this.ship, 5);


        var x = this.player.x;
        var y = this.player.y;

        // if position changed
        if(this.player.oldPosition && (x !== this.player.oldPosition.x || y !== this.player.oldPosition.y) ){

            var jsonObject = {playerUUID: this.playerUUID, x: this.player.x, y: this.player.y };

            this.socket.emit('playerMovement', jsonObject);

            //var jsonObject = {playerUUID: this.playerUUID,
            //    message: String(this.ship.x) + "," + String(this.ship.y) };

            //this.socket.emit('playerMovementAlternative', jsonObject);
        }

        this.player.oldPosition = {
            x: this.player.x,
            y: this.player.y
        };


    }
}


window.onload = () => {

};

function requestPosition(self) {
    var jsonObject = {playerUUID: self.playerUUID,
        message: "get_player_position_based_on_UUID"};

    self.socket.emit('requestPosition', jsonObject);
}

function addPlayer(self) {
    var x = 100;
    var y = 100;

    self.player = self.physics.add.sprite(100, 450, 'dude');

    self.player.setBounce(0.2);
    self.player.setCollideWorldBounds(true);

    self.physics.add.collider(self.player, self.platforms);

    self.anims.create({
        key: 'left',
        frames: self.anims.generateFrameNumbers('dude', { start: 0, end: 3 }),
        frameRate: 10,
        repeat: -1
    });

    self.anims.create({
        key: 'turn',
        frames: [ { key: 'dude', frame: 4 } ],
        frameRate: 20
    });

    self.anims.create({
        key: 'right',
        frames: self.anims.generateFrameNumbers('dude', { start: 5, end: 8 }),
        frameRate: 10,
        repeat: -1
    });

    //self.ship = self.physics.add.image(x, y, 'ship').setOrigin(0.5, 0.5).setDisplaySize(53, 40);
    //self.ship.setAngularDrag(100);
    //self.ship.setMaxVelocity(200);

    //TODO delete redundant
    self.playersArray[self.playerUUID] = {
        x: x,
        y: y
    };

    var jsonObject = {playerUUID: self.playerUUID, message: "requested_data" };

    self.socket.emit('enemyPlayerDataReq', jsonObject);

}

function addEnemyPlayer(self, data) {
    //const otherPlayer = self.add.sprite(x, y, 'otherPlayer').setOrigin(0.5, 0.5).setDisplaySize(53, 40);
    const otherPlayer = self.physics.add.sprite(100, 450, 'dude');

    otherPlayer.setBounce(0.2);
    otherPlayer.setCollideWorldBounds(true);

    self.physics.add.collider(otherPlayer, self.platforms);

    otherPlayer.playerUUID1 = data.playerUUID;
    self.otherPlayers.add(otherPlayer);

    self.playersArray[otherPlayer.playerUUID] = {
        x: x,
        y: y
    }
}

function setup(self) {
    self.playerUUID = 'user' + Math.floor((Math.random()*1000)+1);

    self.socket =  io.connect('http://localhost:9092');

    self.socket.on('newEnemyPlayer', function (data) {
        if(data.playerUUID !== self.playerUUID) {
            addEnemyPlayer(self, data);
        }
    });

    self.socket.on('connect', function() {

        output('<span class="connect-msg">Client has connected to the server!</span>');

        var jsonObject = {playerUUID: self.playerUUID,
            message: "none"};

        console.log("user: " + jsonObject.playerUUID);

        self.socket.emit('newPlayer', jsonObject);
    });

    self.socket.on('chatevent', function(data) {
        output('<span class="username-msg">' + data.userName + ':</span> ' + data.message);
    });

    self.socket.on('disconnect', function() {
        output('<span class="disconnect-msg">The client has disconnected!</span>');
    });

    self.socket.on('enemyPlayerDisconnect', function() {
        output('<span class="disconnect-msg">Enemy player has disconnected!</span>');

        self.otherPlayers.getChildren().forEach(function (enemy) {
            //if (playerId === otherPlayer.playerId) {
                enemy.destroy();
            //}
        });

    });

    self.socket.on('enemyPlayerDataResp', function (data) {
        if(data.x != -1) {
            addEnemyPlayer(self, data);
        }
    });

    self.socket.on('playerMovementResp', function (data) {
        if(data.playerUUID !== self.playerUUID) {
        //TODO fix me
            self.otherPlayers.getChildren().forEach( function (enemy) {
                enemy.setPosition(parseFloat(data.x), parseFloat(data.y));
            })
        }
    });
}


function sendDisconnect() {
    this.socket.disconnect();
}

function sendMessage() {
    var message = $('#msg').val();
    $('#msg').val('');

    var jsonObject = {userName: userName,
        message: message};
    this.socket.emit('chatevent', jsonObject);
}

function output(message) {
    var currentTime = "<span class='time'>" +  moment().format('HH:mm:ss.SSS') + "</span>";
    var element = $("<div>" + currentTime + " " + message + "</div>");
    $('#console').prepend(element);
}

$(document).keydown(function(e){
    if(e.keyCode == 13) {
        $('#send').click();
    }
});

