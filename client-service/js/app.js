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
        preload: preload(),
        create: create(),
        update: update()
    }
};

function preload() {
    //TODO fix me
    this.load.image('ship', '../resources/assets/spaceShips_001.png');
    this.load.image('otherPlayer', '../resources/assets/enemyBlack5.png');
    this.load.image('star', '../resources/assets/star_gold.png');
}


function create(){
    var jsonObject = {userName: userName,
        message: "moj custom"};
    socket.emit('mine_event', jsonObject);

    requestPosition();

}


function update(){

}


window.onload = () => {
    var game = new Phaser.Game(config);
};

function requestPosition() {
    var jsonObject = {playerUUID: playerUUID,
        message: "get_player_position_based_on_UUID"};

    socket.emit('getPosition', jsonObject);
}

function addPlayer(self) {
    self.ship = self.physics.add.image(playerInfo.x, playerInfo.y, 'ship').setOrigin(0.5, 0.5).setDisplaySize(53, 40);
}

function addEnemyPlayer(self, playerObject) {
    const otherPlayer = self.add.sprite(playerInfo.x, playerInfo.y, 'otherPlayer').setOrigin(0.5, 0.5).setDisplaySize(53, 40);
}

