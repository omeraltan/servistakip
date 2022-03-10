import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import MetodTemelUpdatePage from './metod-temel-update.page-object';

const expect = chai.expect;
export class MetodTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.metodTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-metodTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class MetodTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('metod-temel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('metod-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateMetodTemel() {
    await this.createButton.click();
    return new MetodTemelUpdatePage();
  }

  async deleteMetodTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const metodTemelDeleteDialog = new MetodTemelDeleteDialog();
    await waitUntilDisplayed(metodTemelDeleteDialog.deleteModal);
    expect(await metodTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.metodTemel.delete.question/);
    await metodTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(metodTemelDeleteDialog.deleteModal);

    expect(await isVisible(metodTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
