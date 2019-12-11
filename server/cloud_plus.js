const _ = require('lodash');
const Joi = require('joi');
const express = require('express');
const app = express();

// middlewares
app.use(express.json());

// start express server
app.listen(4000, () => console.log('Listening on port 4000.'));

app.get('/api/addition', async (req, res) => {
	console.log(req.body.first_number)
    const { error } =  Joi.validate(req.query, {
        first_number: Joi.number().integer().required(),
        second_number: Joi.number().integer().required(),
    }, {
        abortEarly: false
    });

    if (error) {
        return res.status(400).send({
            message: _.join(_.map(error.details, 'message'), ', '),
        });
    }

    console.log("api/addition GET request")
    console.log("first_number:" + req.query.first_number);
    console.log("second_number:" + req.query.second_number);

    return res.send({
        result_GET: (parseInt(req.query.first_number) + parseInt(req.query.second_number) + " :GET"),
    });
});

app.post('/api/addition', async (req, res) => {
    const { error } =  Joi.validate(req.body, {
        first_number: Joi.number().integer().required(),
        second_number: Joi.number().integer().required(),
    }, {
        abortEarly: false
    });

    if (error) {
        return res.status(400).send({
            message: _.join(_.map(error.details, 'message'), ', '),
        });
    }

    console.log("api/addition POST request")
    console.log("first_number:" + req.body.first_number);
    console.log("second_number:" + req.body.second_number);

    return res.send({
        result_POST: (parseInt(req.body.first_number) + parseInt(req.body.second_number) + " :POST"),
    });
});
