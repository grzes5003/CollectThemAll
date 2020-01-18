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
            gravity: { y: 0 }
        }
    },
    scene: {
        preload: preload,
        create: create,
        update: update
    }
};

var game = new Phaser.Game(config);


function preload() {
    //TODO fix me
    this.load.image('ship', 'resources/assets/spaceShips_001.png');
    this.load.image('otherPlayer', 'resources/assets/enemyBlack5.png');
    this.load.image('star', 'resources/assets/star_gold.png');
}


function create(){
    var self = this;

    this.playersArray = {};

    setup(self);

    this.cursors = this.input.keyboard.createCursorKeys();
    this.otherPlayers = this.physics.add.group();

    addPlayer(self);

    var jsonObject = {userName: this.playerUUID,
        message: "moj custom"};
    self.socket.emit('mine_event', jsonObject);

    requestPosition(self);
}


function update(){
    if (this.ship) {

        // controlls

        // emit new players movment

        // save old position

        if (this.cursors.left.isDown) {
            this.ship.setAngularVelocity(-150);
        } else if (this.cursors.right.isDown) {
            this.ship.setAngularVelocity(150);
        } else {
            this.ship.setAngularVelocity(0);
        }

        if (this.cursors.up.isDown) {
            this.physics.velocityFromRotation(this.ship.rotation + 1.5, 100, this.ship.body.acceleration);
        } else {
            this.ship.setAcceleration(0);
        }

        this.physics.world.wrap(this.ship, 5);


        var x = this.ship.x;
        var y = this.ship.y;

        // if position changed
        if(this.ship.oldPosition && (x !== this.ship.oldPosition.x || y !== this.ship.oldPosition.y) ){
            var jsonObject = {playerUUID: this.playerUUID,
                message: {x: this.ship.x, y: this.ship.y} };
            this.socket.emit('playerMovement', jsonObject);
        }

        this.ship.oldPosition = {
            x: this.ship.x,
            y: this.ship.y
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

    self.ship = self.physics.add.image(x, y, 'ship').setOrigin(0.5, 0.5).setDisplaySize(53, 40);
    self.ship.setAngularDrag(100);
    self.ship.setMaxVelocity(200);

    self.playersArray[self.playerUUID] = {
        x: x,
        y: y
    };
}

function addEnemyPlayer(self, data) {
    var x = 100;
    var y = 100;
    const otherPlayer = self.add.sprite(x, y, 'otherPlayer').setOrigin(0.5, 0.5).setDisplaySize(53, 40);
    otherPlayer.playerUUID = data.playerUUID;
    self.otherPlayers.add(otherPlayer);

    self.playersArray[otherPlayer.playerUUID] = {
        x: x,
        y: y
    }
}

function setup(self) {
    self.playerUUID = 'user' + Math.floor((Math.random()*1000)+1);

    self.socket =  io.connect('http://localhost:9092');

    self.socket.on('connect', function() {

        output('<span class="connect-msg">Client has connected to the server!</span>');

        var jsonObject = {playerUUID: self.playerUUID,
            message: "none"};

        console.log("user: " + jsonObject.toString());

        self.socket.emit('newPlayer', jsonObject);
    });

    self.socket.on('chatevent', function(data) {
        output('<span class="username-msg">' + data.userName + ':</span> ' + data.message);
    });

    self.socket.on('disconnect', function() {
        output('<span class="disconnect-msg">The client has disconnected!</span>');
    });

    self.socket.on('newEnemyPlayer', function (data) {
        if(data.playerUUID !== self.playerUUID) {
            addEnemyPlayer(self, data);
        }
    });

    self.socket.on('playerMovementResp', function (data) {
        if(data.playerUUID !== self.playerUUID) {
            // find player

            // change his position

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

