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

    this.cursors = this.input.keyboard.createCursorKeys();

    addPlayer(self);

    var jsonObject = {userName: playerUUID,
        message: "moj custom"};
    socket.emit('mine_event', jsonObject);
    requestPosition();
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
            this.socket.emit('playerMovement', {playerUUID: playerUUID, message: {x: this.ship.x, y: this.ship.y}});
        }

        this.ship.oldPosition = {
            x: this.ship.x,
            y: this.ship.y
        };


    }
}


window.onload = () => {

};

function requestPosition() {
    var jsonObject = {playerUUID: playerUUID,
        message: "get_player_position_based_on_UUID"};

    socket.emit('requestPosition', jsonObject);
}

function addPlayer(self) {
    self.ship = self.physics.add.image(100, 100, 'ship').setOrigin(0.5, 0.5).setDisplaySize(53, 40);
}

function addEnemyPlayer(self, playerObject) {
    const otherPlayer = self.add.sprite(playerInfo.x, playerInfo.y, 'otherPlayer').setOrigin(0.5, 0.5).setDisplaySize(53, 40);
}

