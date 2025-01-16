import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from '@mui/material';

const InviteDialog = ({ open, onClose, onSubmit, opponent }) => {

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>{`${opponent} vous invite à jouer, voulez-vous accepter l'invitation ?`}</DialogTitle>
      <DialogActions>
        <Button onClick={onClose} color="secondary">
          Refuser
        </Button>
        <Button onClick={onSubmit} color="primary">
          Confirmer
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default InviteDialog;
